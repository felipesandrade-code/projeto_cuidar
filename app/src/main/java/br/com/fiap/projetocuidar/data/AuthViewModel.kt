package br.com.fiap.projetocuidar.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class User(
    val id: String = "",
    val email: String,
    val senha: String = "",
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = "",
    val cpfCnpj: String = "",
    val tipoUsuario: String = "",
    val role: String = "CLIENT",
    val fotoUrl: String? = null
)

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val navigateTo: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

private fun UserResponse.toUser() = User(
    id = id,
    email = email,
    nome = nome,
    sobrenome = sobrenome,
    tipoUsuario = role,
    role = role,
    telefone = "",
    cpfCnpj = ""
)

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val tokenManager = TokenManager(app)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    init {
        viewModelScope.launch {
            val savedToken = tokenManager.getToken() ?: return@launch
            ApiClient.setToken(savedToken)
            try {
                val response = ApiClient.api.me()
                val customType = tokenManager.getUserType(response.user.email)
                val extraData = tokenManager.getExtraData()
                
                _currentUser.value = response.user.toUser().copy(
                    tipoUsuario = customType ?: response.user.role,
                    telefone = extraData.first,
                    cpfCnpj = extraData.second,
                    fotoUrl = extraData.third,
                    role = response.user.role // O servidor manda a verdade absoluta sobre a role
                )
            } catch (e: Exception) {
                tokenManager.clear()
                ApiClient.setToken(null)
            }
        }
    }

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = ApiClient.api.login(LoginRequest(email, senha))
                ApiClient.setToken(response.token)
                
                val customType = tokenManager.getUserType(email)
                val extraData = tokenManager.getExtraData()
                tokenManager.saveSession(response.token, response.user, customType)
                
                _currentUser.value = response.user.toUser().copy(
                    tipoUsuario = customType ?: response.user.role,
                    telefone = extraData.first,
                    cpfCnpj = extraData.second,
                    fotoUrl = extraData.third,
                    role = response.user.role
                )
                _authState.value = AuthState.Success("home")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("E-mail ou senha incorretos.")
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Distinção de Roles:
                // Doador: CLIENT (Doador comum)
                // Voluntário/Orfanato: OPERATOR (Colaboradores ativos)
                val backendRole = when (user.tipoUsuario.lowercase()) {
                    "voluntário", "voluntario", "orfanato" -> "OPERATOR"
                    else -> "CLIENT"
                }

                val response = ApiClient.api.register(
                    RegisterRequest(
                        nome = user.nome,
                        sobrenome = user.sobrenome.ifBlank { "-" },
                        email = user.email,
                        senha = user.senha,
                        telefone = user.telefone.ifBlank { null },
                        role = backendRole
                    )
                )
                ApiClient.setToken(response.token)
                tokenManager.saveSession(response.token, response.user, user.tipoUsuario)
                tokenManager.updateLocalUser(user.telefone, user.cpfCnpj, user.fotoUrl, user.tipoUsuario, backendRole)
                
                _currentUser.value = response.user.toUser().copy(
                    cpfCnpj = user.cpfCnpj,
                    tipoUsuario = user.tipoUsuario,
                    telefone = user.telefone,
                    fotoUrl = user.fotoUrl,
                    role = response.user.role // Usamos o que o servidor devolveu
                )
                
                val dest = when (user.tipoUsuario.lowercase()) {
                    "doador" -> "registro_doador"
                    "voluntário", "voluntario" -> "registro_voluntario"
                    "orfanato" -> "registerOng"
                    else -> "home"
                }
                _authState.value = AuthState.Success(dest)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Erro ao criar conta: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clear()
            ApiClient.setToken(null)
            _currentUser.value = null
            _authState.value = AuthState.Idle
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

    fun updateUser(user: User) {
        _currentUser.value = user
        viewModelScope.launch {
            tokenManager.updateLocalUser(user.telefone, user.cpfCnpj, user.fotoUrl, user.tipoUsuario, user.role)
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(app) as T
        }
    }
}

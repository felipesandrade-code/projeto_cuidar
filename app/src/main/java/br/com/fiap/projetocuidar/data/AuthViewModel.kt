package br.com.fiap.projetocuidar.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.ApiClient
import br.com.fiap.projetocuidar.data.network.LoginRequest
import br.com.fiap.projetocuidar.data.network.RegisterRequest
import br.com.fiap.projetocuidar.data.network.TokenManager
import br.com.fiap.projetocuidar.data.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class User(
    val email: String,
    val senha: String = "",
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = "",
    val cpfCnpj: String = "",
    val tipoUsuario: String = ""
)

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val navigateTo: String = "home") : AuthState()
    data class Error(val message: String) : AuthState()
}

private fun UserResponse.toUser() = User(
    email = email,
    nome = nome,
    sobrenome = sobrenome,
    tipoUsuario = role
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
                _currentUser.value = response.user.toUser()
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
                tokenManager.saveSession(response.token, response.user)
                _currentUser.value = response.user.toUser()
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
                val response = ApiClient.api.register(
                    RegisterRequest(
                        nome = user.nome,
                        sobrenome = user.sobrenome.ifBlank { "-" },
                        email = user.email,
                        senha = user.senha,
                        telefone = user.telefone.ifBlank { null },
                        role = "CLIENT"
                    )
                )
                ApiClient.setToken(response.token)
                tokenManager.saveSession(response.token, response.user)
                _currentUser.value = response.user.toUser().copy(
                    cpfCnpj = user.cpfCnpj,
                    tipoUsuario = user.tipoUsuario,
                    telefone = user.telefone
                )
                val dest = when (user.tipoUsuario) {
                    "Doador" -> "registro_doador"
                    "Voluntário" -> "registro_voluntario"
                    else -> "home"
                }
                _authState.value = AuthState.Success(dest)
            } catch (e: Exception) {
                val msg = when {
                    e.message?.contains("409") == true -> "Este e-mail já está cadastrado."
                    e.message?.contains("400") == true -> "Dados inválidos. Verifique e tente novamente."
                    else -> "Erro ao criar conta. Verifique a conexão."
                }
                _authState.value = AuthState.Error(msg)
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
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(app) as T
        }
    }
}

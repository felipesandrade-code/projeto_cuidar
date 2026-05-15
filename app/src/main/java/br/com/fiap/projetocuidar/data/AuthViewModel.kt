package br.com.fiap.projetocuidar.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class User(
    val email: String,
    val senha: String,
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = ""
)

class AuthViewModel : ViewModel() {
    private val _users = mutableListOf<User>()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun register(user: User): Boolean {
        if (_users.any { it.email == user.email }) {
            return false
        }
        _users.add(user)
        return true
    }

    fun login(email: String, senha: String): Boolean {
        val user = _users.find { it.email == email && it.senha == senha }
        if (user != null) {
            _currentUser.value = user
            return true
        }
        return false
    }

    fun loginAsGuest() {
        _currentUser.value = User(email = "convidado@cuidar.com", senha = "", nome = "Convidado")
    }

    fun loginWithGoogle(email: String, displayName: String) {
        // No mundo real, aqui você validaria o ID Token com seu backend
        val user = User(email = email, senha = "", nome = displayName)
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }
}
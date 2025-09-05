package br.com.fiap.projetocuidar

import androidx.compose.runtime.Composable
import android.util.Patterns
import androidx.compose.ui.Modifier

@Composable
fun ValidateEmail(email: String): String? {
    return when {
        email.isBlank() -> "O campo e-mail não pode ficar vazio"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Digite um e-mail válido"
        else -> null // válido
    }
}

@Composable
fun validateSenha(senha: String): String? {
    return when {
        senha.isBlank() -> "O campo senha não pode ficar vazio"
        senha.length < 6 -> "A senha deve ter no mínimo 6 caracteres"
        else -> null
    }
}
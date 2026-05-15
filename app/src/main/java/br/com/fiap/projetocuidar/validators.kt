package br.com.fiap.projetocuidar

fun ValidateEmail(email: String): String? {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+$".toRegex()
    return when {
        email.isBlank() -> "O campo e-mail não pode ficar vazio"
        !emailRegex.matches(email) -> "Digite um e-mail válido"
        else -> null
    }
}

fun validateSenha(senha: String): String? {
    val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$".toRegex()
    return when {
        senha.isBlank() -> "O campo senha não pode ficar vazio"
        senha.length < 8 -> "A senha deve ter no mínimo 8 caracteres"
        !regex.containsMatchIn(senha) -> "A senha deve conter letras maiúsculas, minúsculas, números e caracteres especiais"
        else -> null
    }
}

fun validateTelefone(telefone: String): String? {
    val digitsOnly = telefone.filter { it.isDigit() }
    return when {
        digitsOnly.isBlank() -> "O campo telefone não pode ficar vazio"
        digitsOnly.length < 10 || digitsOnly.length > 11 -> "Telefone inválido (DDD + número)"
        else -> null
    }
}
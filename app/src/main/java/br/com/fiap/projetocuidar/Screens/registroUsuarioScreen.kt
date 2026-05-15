package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.registroUsuario.RegisterComponents
import br.com.fiap.projetocuidar.data.AuthViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroUsuarioScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    RegisterComponents(navcontroller = navController, authViewModel = authViewModel)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroScrenPreview() {
    RegistroUsuarioScreen(navController = NavController(LocalContext.current))
}
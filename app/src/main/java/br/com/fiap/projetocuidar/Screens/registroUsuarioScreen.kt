package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.registroUsuario.RegisterComponents

@Composable
fun RegistroUsuarioScreen(navController: NavController) {
    RegisterComponents(navcontroller = navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroScrenPreview() {
    RegistroUsuarioScreen(navController = NavController(LocalContext.current))
}
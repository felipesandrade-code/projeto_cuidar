package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.login.LoginComponents

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    LoginComponents(navController = navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(navController = NavController(LocalContext.current))
}
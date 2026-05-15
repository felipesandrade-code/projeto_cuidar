package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.cadastroDoador.CadastroDoadorComponents

@Composable
fun CadastroDoadorScreen(navController: NavController) {
    CadastroDoadorComponents(navController = navController)
}

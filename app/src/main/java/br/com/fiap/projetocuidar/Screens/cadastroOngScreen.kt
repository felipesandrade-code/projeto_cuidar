package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.cadastroOng.CadastroOngComponents


@Composable
fun CadastroOngScreen(modifier: Modifier = Modifier, navController: NavController.Companion) {
    CadastroOngComponents(navController = navController)
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
private fun CadastroOngScreenPreview() {
    CadastroOngScreen(Modifier, navController = NavController)
}
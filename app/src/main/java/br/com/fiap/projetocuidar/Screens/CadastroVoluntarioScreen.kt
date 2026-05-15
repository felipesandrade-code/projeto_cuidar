package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.cadastroVoluntario.CadastroVoluntarioComponents
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun CadastroVoluntarioScreen(navController: NavController, orphanageViewModel: OrphanageViewModel) {
    CadastroVoluntarioComponents(navController = navController, orphanageViewModel = orphanageViewModel)
}

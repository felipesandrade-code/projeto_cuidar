package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.doacao.DoacaoComponents
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun DoacaoScreen(navController: NavController, orphanageViewModel: OrphanageViewModel) {
    DoacaoComponents(navController = navController, orphanageViewModel = orphanageViewModel)
}

package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.inscricao.InscricaoComponents
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun InscricaoScreen(navController: NavController, orphanageViewModel: OrphanageViewModel) {
    InscricaoComponents(navController = navController, orphanageViewModel = orphanageViewModel)
}

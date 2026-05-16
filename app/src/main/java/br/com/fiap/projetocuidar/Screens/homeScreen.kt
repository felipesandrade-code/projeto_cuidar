package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.home.HomeComponents
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel, orphanageViewModel: OrphanageViewModel) {
    LaunchedEffect(Unit) {
        orphanageViewModel.loadOrphanages()
    }
    HomeComponents(
        modifier = Modifier, 
        navController = navController, 
        authViewModel = authViewModel,
        orphanageViewModel = orphanageViewModel
    )
}
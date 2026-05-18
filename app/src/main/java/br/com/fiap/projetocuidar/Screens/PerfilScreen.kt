package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.perfil.PerfilComponents
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun PerfilScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel
) {
    PerfilComponents(
        navController = navController,
        authViewModel = authViewModel,
        orphanageViewModel = orphanageViewModel
    )
}

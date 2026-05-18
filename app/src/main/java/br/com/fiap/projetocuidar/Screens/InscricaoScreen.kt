package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.inscricao.InscricaoComponents
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel

@Composable
fun InscricaoScreen(
    navController: NavController, 
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel,
    authViewModel: AuthViewModel
) {
    InscricaoComponents(
        navController = navController, 
        orphanageViewModel = orphanageViewModel,
        managementViewModel = managementViewModel,
        authViewModel = authViewModel
    )
}

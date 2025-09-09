package br.com.fiap.projetocuidar.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.projetocuidar.Screens.CadastroOngScreen

import br.com.fiap.projetocuidar.Screens.LoginScreen
import br.com.fiap.projetocuidar.Screens.MapaScreen
import br.com.fiap.projetocuidar.Screens.MapaSelectScreen
import br.com.fiap.projetocuidar.Screens.OrfanatoDetalheScreen
import br.com.fiap.projetocuidar.Screens.RegistroUsuarioScreen
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun AppNavHost(playServicesOk: Boolean) {
    val app = LocalContext.current.applicationContext as Application
    val vm: OrphanageViewModel = viewModel (factory = OrphanageViewModel.Factory(app))

    val nav = androidx.navigation.compose.rememberNavController()

    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(nav) }
        composable("registro"){ RegistroUsuarioScreen(nav) }
        composable("mapa") { MapaScreen(nav, vm) }

        composable("map_select") { MapaSelectScreen(nav, vm) }
        composable("registerOng") { CadastroOngScreen( nav, vm) }
        composable("ong_detail") {
            OrfanatoDetalheScreen(nav, vm)
        }
    }
}
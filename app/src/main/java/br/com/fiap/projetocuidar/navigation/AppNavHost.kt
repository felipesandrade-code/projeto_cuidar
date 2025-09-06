package br.com.fiap.projetocuidar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import br.com.fiap.projetocuidar.Screens.HomeScreen
import br.com.fiap.projetocuidar.Screens.LoginScreen
import br.com.fiap.projetocuidar.Screens.MapaScreen
import br.com.fiap.projetocuidar.Screens.OrfanatoAmorDeCristoScreen
import br.com.fiap.projetocuidar.Screens.OrfanatoEsperancaScreen
import br.com.fiap.projetocuidar.Screens.RegistroUsuarioScreen

@Composable
fun AppNavHost(playServicesOk: Boolean) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(nav) }
        composable("home")  { HomeScreen(playServicesOk = playServicesOk, Modifier, nav) }
        composable("registro"){ RegistroUsuarioScreen(nav) }
        composable("mapa") { MapaScreen(nav) }
        composable("orfanatoEsperanca") { OrfanatoEsperancaScreen( nav)}
        composable("orfanatoAmorDeCristo") { OrfanatoAmorDeCristoScreen(nav) }

    }
}
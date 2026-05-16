package br.com.fiap.projetocuidar.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.projetocuidar.Screens.CadastroDoadorScreen
import br.com.fiap.projetocuidar.Screens.CadastroOngScreen
import br.com.fiap.projetocuidar.Screens.CadastroVoluntarioScreen
import br.com.fiap.projetocuidar.Screens.DoacaoScreen
import br.com.fiap.projetocuidar.Screens.HomeScreen
import br.com.fiap.projetocuidar.Screens.InscricaoScreen
import br.com.fiap.projetocuidar.Screens.LoginScreen
import br.com.fiap.projetocuidar.Screens.MapaScreen
import br.com.fiap.projetocuidar.Screens.MapaSelectScreen
import br.com.fiap.projetocuidar.Screens.OrfanatoDetalheScreen
import br.com.fiap.projetocuidar.Screens.PerfilScreen
import br.com.fiap.projetocuidar.Screens.RegistroUsuarioScreen
import br.com.fiap.projetocuidar.Screens.ChatScreen
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.MessageViewModel

@Composable
fun AppNavHost(playServicesOk: Boolean) {
    val app = LocalContext.current.applicationContext as Application
    val vm: OrphanageViewModel = viewModel(factory = OrphanageViewModel.Factory(app))
    val authVm: AuthViewModel = viewModel(factory = AuthViewModel.Factory(app))
    val messageVm: MessageViewModel = viewModel()

    val nav = androidx.navigation.compose.rememberNavController()

    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(nav, authVm) }
        composable("registro") { RegistroUsuarioScreen(nav, authVm) }
        composable("registro_doador") { CadastroDoadorScreen(nav) }
        composable("registro_voluntario") { CadastroVoluntarioScreen(nav, vm) }
        composable("home") { HomeScreen(nav, authVm, vm) }
        composable("perfil") { PerfilScreen(nav, authVm) }
        composable("doacao") { DoacaoScreen(nav, vm) }
        composable("inscricao") { InscricaoScreen(nav, vm) }
        composable("mapa") { MapaScreen(nav, vm) }
        composable("map_select") { MapaSelectScreen(nav, vm) }
        composable("registerOng") { CadastroOngScreen(nav, vm, authVm) }
        composable("ong_detail") { OrfanatoDetalheScreen(nav, vm) }
        composable("chat/{orphanageId}/{orphanageName}") { backStackEntry ->
            val orphanageId = backStackEntry.arguments?.getString("orphanageId") ?: ""
            val orphanageName = backStackEntry.arguments?.getString("orphanageName") ?: ""
            ChatScreen(nav, orphanageId, orphanageName, authVm, messageVm)
        }
    }
}

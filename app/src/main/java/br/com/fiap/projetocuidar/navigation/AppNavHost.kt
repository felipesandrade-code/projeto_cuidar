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
import br.com.fiap.projetocuidar.Screens.operator.MessagesScreen
import br.com.fiap.projetocuidar.Screens.operator.CustomersScreen
import br.com.fiap.projetocuidar.Screens.operator.CustomerDetailScreen
import br.com.fiap.projetocuidar.Screens.operator.SegmentsScreen
import br.com.fiap.projetocuidar.Screens.operator.AuditLogsScreen
import br.com.fiap.projetocuidar.Screens.operator.StatisticsScreen
import br.com.fiap.projetocuidar.Screens.operator.ManageTasksScreen
import br.com.fiap.projetocuidar.Screens.operator.ManageVolunteersScreen
import br.com.fiap.projetocuidar.Screens.operator.ManageNewsScreen
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.MessageViewModel
import br.com.fiap.projetocuidar.data.CampaignViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel
import br.com.fiap.projetocuidar.data.SegmentViewModel
import br.com.fiap.projetocuidar.data.CustomerViewModel
import br.com.fiap.projetocuidar.data.AuditViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun AppNavHost(playServicesOk: Boolean) {
    val app = LocalContext.current.applicationContext as Application
    val vm: OrphanageViewModel = viewModel(factory = OrphanageViewModel.Factory(app))
    val authVm: AuthViewModel = viewModel(factory = AuthViewModel.Factory(app))
    val messageVm: MessageViewModel = viewModel(factory = MessageViewModel.Factory(app))
    val campaignVm: CampaignViewModel = viewModel()
    val segmentVm: SegmentViewModel = viewModel()
    val customerVm: CustomerViewModel = viewModel()
    val auditVm: AuditViewModel = viewModel()
    val managementVm: ManagementViewModel = viewModel(factory = ManagementViewModel.Factory(app))

    val nav = androidx.navigation.compose.rememberNavController()

    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(nav, authVm) }
        composable("registro") { RegistroUsuarioScreen(nav, authVm) }
        composable("registro_doador") { CadastroDoadorScreen(nav) }
        composable("registro_voluntario") { CadastroVoluntarioScreen(nav, vm) }
        composable("home") { HomeScreen(nav, authVm, vm, managementVm) }
        composable("perfil") { PerfilScreen(nav, authVm, vm) }
        composable("doacao") { DoacaoScreen(nav, vm, managementVm, authVm) }
        composable("inscricao") { InscricaoScreen(nav, vm, managementVm, authVm) }
        composable("mapa") { MapaScreen(nav, vm) }
        composable("map_select") { MapaSelectScreen(nav, vm) }
        composable("registerOng") { CadastroOngScreen(nav, vm, authVm) }
        composable("ong_detail") { OrfanatoDetalheScreen(nav, vm) }
        composable("chat/{orphanageId}/{orphanageName}") { backStackEntry ->
            val orphanageId = backStackEntry.arguments?.getString("orphanageId") ?: ""
            val orphanageName = backStackEntry.arguments?.getString("orphanageName") ?: ""
            ChatScreen(nav, orphanageId, orphanageName, authVm, messageVm)
        }
        composable("operator_messages") {
            val user = authVm.currentUser.collectAsState().value
            MessagesScreen(nav, user?.id ?: "", isOrfanato = true, messageVm, campaignVm, segmentVm, vm, managementVm, customerVm)
        }
        composable("chat_inbox") {
            val user = authVm.currentUser.collectAsState().value
            MessagesScreen(nav, user?.id ?: "", isOrfanato = false, messageVm, campaignVm, segmentVm, vm, managementVm, customerVm)
        }
        composable("operator_customers") {
            CustomersScreen(nav, customerVm) { customer ->
                customerVm.selectCustomer(customer)
                nav.navigate("operator_customer_detail")
            }
        }
        composable("operator_customer_detail") {
            val customer = customerVm.selectedCustomer.collectAsState().value
            if (customer != null) {
                CustomerDetailScreen(nav, customer, customerVm, messageVm)
            } else {
                nav.popBackStack()
            }
        }
        composable("operator_segments") {
            SegmentsScreen(nav, segmentVm)
        }
        composable("operator_audit_logs") {
            AuditLogsScreen(nav, auditVm, authVm)
        }
        composable("operator_statistics") {
            StatisticsScreen(nav, authVm, vm, managementVm)
        }
        composable("operator_manage_tasks") {
            ManageTasksScreen(nav, authVm, vm, managementVm)
        }
        composable("operator_manage_volunteers") {
            ManageVolunteersScreen(nav, authVm, vm, managementVm, messageVm)
        }
        composable("operator_manage_news") {
            ManageNewsScreen(nav, authVm, vm, managementVm)
        }
    }
}

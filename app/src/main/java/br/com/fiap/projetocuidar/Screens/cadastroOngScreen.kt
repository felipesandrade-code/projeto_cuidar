package br.com.fiap.projetocuidar.Screens

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.cadastroOrfanato.CadastroOngComponents
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel


@Composable
fun CadastroOngScreen(navController: NavController, vm: OrphanageViewModel, authViewModel: AuthViewModel) {
    CadastroOngComponents(navController = navController, vm, authViewModel)
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
private fun CadastroOngScreenPreview() {
    val app = LocalContext.current.applicationContext as Application
    val vm: OrphanageViewModel = viewModel (factory = OrphanageViewModel.Factory(app))
    val authVm: AuthViewModel = viewModel (factory = AuthViewModel.Factory(app))

    CadastroOngScreen(navController = NavController(LocalContext.current), vm, authVm)
}
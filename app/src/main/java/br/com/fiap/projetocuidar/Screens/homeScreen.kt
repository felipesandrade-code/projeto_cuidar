package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.components.home.HomeComponents

@Composable
fun HomeScreen(playServicesOk: Boolean, modifier: Modifier = Modifier, navController: NavController) {
    HomeComponents(Modifier, navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        playServicesOk = true, modifier = Modifier,
        navController = rememberNavController()
    )
}

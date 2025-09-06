package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.components.orfanatos.OrfanatoAmorDeCristo

@Composable
fun OrfanatoAmorDeCristoScreen(navController: NavController) {
    OrfanatoAmorDeCristo(Modifier, navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OngScreenPreview() {
    OrfanatoAmorDeCristoScreen(navController = rememberNavController())
}
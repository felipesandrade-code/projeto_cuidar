package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.projetocuidar.components.home.HomeComponents

@Composable
fun HomeScreen(playServicesOk: Boolean, modifier: Modifier = Modifier) {
    HomeComponents()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        playServicesOk = true, modifier = Modifier
    )
}

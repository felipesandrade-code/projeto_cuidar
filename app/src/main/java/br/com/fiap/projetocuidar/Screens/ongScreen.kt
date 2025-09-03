package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.projetocuidar.components.ong.OngComponents

@Composable
fun OngScreen(modifier: Modifier = Modifier) {
    OngComponents()
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OngScreenPreview() {
    OngScreen()
}
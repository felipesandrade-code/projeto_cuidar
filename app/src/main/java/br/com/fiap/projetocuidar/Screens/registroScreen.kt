package br.com.fiap.projetocuidar.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.projetocuidar.composables.ColumnRegister

@Composable
fun RegistroScreen(modifier: Modifier = Modifier) {
    ColumnRegister()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroScrenPreview() {
    RegistroScreen()
}
package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.composables.CardFooter
import br.com.fiap.projetocuidar.composables.ColumnRegister

@Composable
fun RegistroScreen(modifier: Modifier = Modifier) {
    ColumnRegister()
    Box(
        modifier = Modifier
            .absoluteOffset(y = 680.dp)
            .padding(top = 50.dp)
    ) {
        CardFooter(modifier = Modifier.align(alignment = Alignment.Center))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroScrenPreview() {
    RegistroScreen()
}
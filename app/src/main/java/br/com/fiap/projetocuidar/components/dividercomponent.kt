package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividerComponent(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = Modifier
            .padding(start = 30.dp, end = 60.dp)
            .width(300.dp),
        thickness = DividerDefaults.Thickness,
        color = DividerDefaults.color
    )
}
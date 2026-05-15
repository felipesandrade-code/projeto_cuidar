package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import br.com.fiap.projetocuidar.R

@Composable
fun LogoComponent(
    contentDescription: String,
    logoSize: Dp,
    logoOffsetX: Dp,
    logoOffsetY: Dp,
) {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(logoSize)
            .offset(y = logoOffsetY, x = logoOffsetX),
        contentScale = ContentScale.Fit,
    )
}

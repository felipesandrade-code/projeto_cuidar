package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun ImgComponent(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.kids),
        contentDescription = "Crianças abraçadas",
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 220.dp)
            .aspectRatio(464f / 450f),
        contentScale = ContentScale.Crop,
    )
}

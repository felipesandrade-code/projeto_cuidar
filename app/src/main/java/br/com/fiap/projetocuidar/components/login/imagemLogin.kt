package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun ImagemLogin(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.kids_happy),
        contentDescription = "Crianças abraçadas",
        modifier = Modifier
            .size(700.dp)
            .offset(y = -150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .drawWithContent {
                drawContent()

                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(0.5f),
                            Color.Black.copy(0.5f)
                        ),
                        startY = 0f,
                        endY = size.height / 3
                    ),
                    size = size
                )
            }
    )
}
package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun LogoLogin(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo cuidar+",
        modifier = Modifier
            .size(140.dp)
            .offset(y = 20.dp, x = 20.dp)
    )
}
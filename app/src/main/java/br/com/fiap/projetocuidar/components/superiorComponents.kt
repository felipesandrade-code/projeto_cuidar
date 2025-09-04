package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun SuperiorCadastroOng(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.white))
            .height(85.dp)
            .offset(y = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.seta_esquerda_back_24),
            contentDescription = "Seta para esquerda",
            modifier = Modifier
                .size(25.dp)
                .align(alignment = Alignment.CenterVertically)
                .offset(x = -120.dp),
            tint = colorResource(R.color.cor_registre)
        )
        Text(
            text = "Orfanato",
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            fontSize = 17.sp
        )
    }
}

@Composable
fun SuperiorOngComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color = colorResource(R.color.white)),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.seta_esquerda_back_24),
            contentDescription = "Seta para esquerda",
            modifier = Modifier
                .size(25.dp)
                .align(alignment = Alignment.CenterVertically)
                .offset(y = 8.dp, x = -120.dp),
            tint = colorResource(R.color.cor_registre)
        )
        Text(
            text = "Orfanato",
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .offset(y = 8.dp),
            fontSize = 17.sp
        )
    }
}

@Composable
fun SuperiorRegister(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = colorResource(R.color.white))
            .offset(y = 10.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.seta_esquerda_back_24),
            contentDescription = "Seta para esquerda",
            modifier = Modifier
                .size(25.dp)
                .align(alignment = Alignment.CenterVertically)
                .offset(x = -130.dp),
            tint = colorResource(R.color.cor_registre)
        )
        Text(
            text = "Login",
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            fontSize = 17.sp
        )
    }
}
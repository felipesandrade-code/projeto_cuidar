package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R

@Composable
fun TextsLogin(
    modifier: Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 15.sp,
        color = colorResource(R.color.cor_text_login),
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        modifier = Modifier.offset(x = 30.dp)
    )
}

@Composable
fun TextOngComponent(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign,
    padding: Dp,
    fontFamily: FontFamily,
){
    Text(
        text = text,
        fontFamily = fontFamily,
        fontSize = 13.sp,
        color = colorResource(R.color.texto_orfanato),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        textAlign = textAlign
    )
}

@Composable
fun TextTituloOngComponent(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        fontSize = 25.sp,
        color = colorResource(R.color.titulo),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextHomeComponent(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    fontFamily: FontFamily,
    offsetX: Dp,
    offsetY: Dp
) {
    Text(
        text = text,
        modifier = Modifier.offset(offsetX, offsetY),
        color = colorResource(R.color.cor_registre),
        fontSize = fontSize,
        fontFamily = fontFamily,
    )
}

@Composable
fun TextCadastroOng(
    text: String,
){
    Text(
        text = text,
        fontSize = 15.sp,
        color = colorResource(R.color.cor_text_login),
        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
        modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
    )
}

@Composable
fun TextClickable(
    modifier: Modifier = Modifier,
    text: String,
    navcontroller: NavController,
    route: String,
    fontSize: TextUnit,
    offsetX: Dp,
    offsetY: Dp
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = colorResource(R.color.cor_registre),
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .clickable(
                onClick = {navcontroller.navigate(route)}
            )
    )
}

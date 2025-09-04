package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
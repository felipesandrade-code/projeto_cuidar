package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.padding
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
fun TextButtons(
    text: String
) {
    Text(
        text = text,
        fontSize = 15.sp,
        color = colorResource(R.color.white),
        fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
        modifier = Modifier.padding(horizontal = 30.dp)
    )
}
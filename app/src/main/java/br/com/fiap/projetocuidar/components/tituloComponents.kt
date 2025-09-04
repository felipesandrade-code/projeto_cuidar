package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun TituloComponents(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit
) {
    Text(
        text = text,
        fontSize = 25.sp,
        color = colorResource(R.color.cor_registre),
        fontFamily = FontFamily(Font(R.font.nunito_bold)),
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp)
    )
}
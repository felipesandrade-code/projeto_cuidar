package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun ButtonsComponent(
    modifier: Modifier,
    buttonwidth: Dp,
    buttonOffsetX: Dp,
    buttonOffsetY: Dp,
    onClick: () -> Unit,
    text: String,
    singleLine: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(buttonwidth)
            .offset(x = buttonOffsetX, y = buttonOffsetY),
        shape = Shapes().small,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer)),
    ){
        TextButtons(
            text = text,
            singleLine = singleLine
        )
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_card_footer))
    ) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            fontSize = 16.sp
        )
    }
}

@Composable
fun ButtonVerMapa(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = Modifier
            .offset(y = 107.dp)
            .width(300.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_ver_mapa)),
    )
    {
        Text(
            text = "Ver rotas no Google Maps",
            textAlign = TextAlign.Center,
            color = colorResource(R.color.cor_texto_ver_mapa),
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            fontSize = 12.sp
        )
    }

}
package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
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
fun BotaoLogin(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .width(280.dp)
            .offset(y = 20.dp, x = 55.dp),
        shape = Shapes().small,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer))
    ) {
        Text(
            text = "Entrar",
            fontSize = 15.sp,
            color = colorResource(R.color.white),
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }
}
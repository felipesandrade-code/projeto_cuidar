package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun CardComponentOng(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    tint: Color,
    cardColors: Color,
    text: String,
    colorText: Color,
) {
    Card(
        colors = CardDefaults.cardColors(cardColors),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .size(140.dp),
    ){
        Icon(
            painter = painter,
            contentDescription = contentDescription ,
            modifier = Modifier
                .size(40.dp)
                .offset(y = 12.dp, x = 12.dp),
            tint = tint
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.nunito_semibold)),
            modifier = Modifier
                .padding(end = 9.dp, start = 9.dp),
            color = colorText
        )
    }
}
package br.com.fiap.projetocuidar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun CardFooter(modifier: Modifier = Modifier){
    Column(modifier = Modifier
        .background(
            color = colorResource(R.color.cor_card_footer),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        )
        .padding(bottom = 100.dp, top = 20.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "®cuidar+",
            fontSize = 23.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            color = colorResource(R.color.white),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Todos os direitos reservados",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            maxLines = 1,
            color = colorResource(R.color.white)
            )
    }
}
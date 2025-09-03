package br.com.fiap.projetocuidar.components.ong

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun OngComponents(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(color = colorResource(R.color.white))
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.seta_esquerda_back_24),
                contentDescription = "Seta para esquerda",
                modifier = Modifier
                    .size(25.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .offset(y= 8.dp, x = -120.dp),
                tint = colorResource(R.color.cor_registre)
            )
            Text(
                text = "Orfanato",
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                color = colorResource(R.color.cor_text_login),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .offset(y=8.dp),
                fontSize = 17.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 70.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Orf. Esperança",
                color = colorResource(R.color.titulo),
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Presta assistência a crianças de 06 a 15 anos que se encontre em situação de risco e/ou vulnerabilidade social.",
                color = colorResource(R.color.texto_orfanato),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                fontSize = 13.sp,
                minLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .align(Alignment.BottomCenter)
                            .background(color = colorResource(R.color.cor_ver_mapa)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ver rotas no Google Maps",
                            textAlign = TextAlign.Center,
                            color = colorResource(R.color.cor_texto_ver_mapa),
                            fontFamily = FontFamily(Font(R.font.nunito_bold)),
                            fontSize = 12.sp
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Instruções para visita",
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 18.sp,
                color = colorResource(R.color.titulo),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Venha como se sentir a vontade e traga muito amor e paciência para dar.",
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                fontSize = 13.sp,
                color = colorResource(R.color.texto_orfanato),
                modifier = Modifier.padding(5.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_horario_orfanato)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .size(140.dp)
                ){
                    Icon(
                        painter = painterResource(R.drawable.icon_clock),
                        contentDescription = "Ícone horário",
                        modifier = Modifier
                            .size(40.dp)
                            .offset(y = 12.dp, x = 12.dp),
                        tint = colorResource(R.color.icone_horario)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Segunda à sexta 8h às 18h",
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                        modifier = Modifier
                            .padding(end = 9.dp, start = 9.dp),
                        color = colorResource(R.color.text_horario_orfanato)
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_fim_de_semana)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .size(140.dp),
                ){
                    Icon(
                        painter = painterResource(R.drawable.icons_info),
                        contentDescription = "Ícone informação",
                        modifier = Modifier
                            .size(40.dp)
                            .offset(y = 12.dp, x = 12.dp),
                        tint = colorResource(R.color.icone_info)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Atendemos no fim de semana!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(end = 12.dp, start = 12.dp)
                        ,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                        color = colorResource(R.color.text_fim_de_semana)
                    )
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 60.dp)
                ,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer)),
                shape = RoundedCornerShape(8.dp)
            ){
                Row(
                    modifier = Modifier
                        .width(250.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        painter = painterResource(R.drawable.icon_whatsapp),
                        contentDescription = "Ícone whatsapp",
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterVertically)
                        ,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Entrar em contato",
                        color = colorResource(R.color.white),
                        fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ColumnOngPreview() {
    OngComponents()
}
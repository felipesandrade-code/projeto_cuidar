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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import br.com.fiap.projetocuidar.components.ButtonsComponent
import br.com.fiap.projetocuidar.components.CardComponentOng
import br.com.fiap.projetocuidar.components.SuperiorOngComponent
import br.com.fiap.projetocuidar.components.TextOngComponent
import br.com.fiap.projetocuidar.components.TextTituloOngComponent

@Composable
fun OngComponents(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    )
    {
        SuperiorOngComponent()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 70.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            TextTituloOngComponent(Modifier,"Orf. Esperança")
            TextOngComponent(
                Modifier,
                "Presta assistência a crianças de 06 a 15 anos que se encontre em situação de risco e/ou vulnerabilidade social.",
                TextAlign.Center,
                padding = 20.dp,
                FontFamily(Font(R.font.nunito_semibold)),
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
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(y = 55.dp)
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
            }
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextTituloOngComponent(Modifier,"Instruções para visita")
                TextOngComponent(
                    Modifier,
                    "Venha como se sentir e traga muito amor!",
                    TextAlign.Center,
                    padding = 5.dp,
                    FontFamily(Font(R.font.nunito_semibold)),
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CardComponentOng(
                    modifier = Modifier,
                    painterResource(R.drawable.icon_clock),
                    "Ícone horário",
                    colorResource(R.color.icone_horario),
                    colorResource(R.color.card_horario_orfanato),
                    "Segunda à sexta 8h às 18h",
                    colorResource(R.color.text_horario_orfanato)
                )
                Spacer(modifier = Modifier.width(40.dp))
                CardComponentOng(
                    painter = painterResource(R.drawable.icons_info),
                    contentDescription = "Ícone informação",
                    tint = colorResource(R.color.icone_info),
                    cardColors = colorResource(R.color.card_fim_de_semana),
                    text = "Atendemos no fim de semana!",
                    colorText = colorResource(R.color.text_fim_de_semana)
                )
            }
            ButtonsComponent(
                modifier = Modifier,
                buttonwidth = 250.dp,
                buttonOffsetX = 80.dp,
                buttonOffsetY = 80.dp,
                onClick = {/*TODO*/ },
                text = "Entrar em contato",
                singleLine = true
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ColumnOngPreview() {
    OngComponents()
}
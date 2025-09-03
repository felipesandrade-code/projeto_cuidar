package br.com.fiap.projetocuidar.Screens

import android.graphics.Color
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R


@Composable
fun CadastroOngScreen(modifier: Modifier = Modifier) {
    val nome = remember { mutableStateOf("") }
    val categoria = remember { mutableStateOf("") }
    val sobre = remember { mutableStateOf("") }
    val telefone = remember { mutableStateOf("") }
    val foto = remember { mutableStateOf("") }
    var fimDeSemana by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .background(colorResource(R.color.cor_column_registre))
        .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.white))
                .align(Alignment.TopCenter)
                .height(85.dp),
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.seta_esquerda_back_24),
                contentDescription = "Seta para esquerda",
                modifier = Modifier
                    .size(25.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .offset(y = 8.dp, x = -120.dp),
                tint = colorResource(R.color.cor_registre)
            )
            Text(
                text = "Orfanato",
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                color = colorResource(R.color.cor_text_login),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .offset(y = 8.dp),
                fontSize = 17.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 80.dp)
        ) {
            Text(
                "Dados",
                fontSize = 25.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 30.dp, end = 60.dp)
                    .width(300.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Text(
                text = "Nome",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
            )
            OutlinedTextField(
                value = nome.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                "Categoria",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                "Sobre",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(100.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                "Telefone",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                "Foto (url)",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                "Visitação",
                fontSize = 25.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 30.dp, end = 60.dp)
                    .width(300.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Instruções ",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(90.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                "Horário das visitas",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, top = 2.dp)
            )
            OutlinedTextField(
                value = categoria.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(start = 10.dp, top = 2.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .offset(y = 15.dp)
            ){
                Text(
                    text = "Atende fim de semana?",
                    fontSize = 15.sp,
                    color = colorResource(R.color.cor_text_login),
                    fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                    modifier = Modifier.padding(start = 32.dp)
                )
                Spacer(modifier = Modifier.width(80.dp))
                Switch(
                    checked = fimDeSemana,
                    onCheckedChange = {fimDeSemana = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.white),
                        checkedTrackColor = colorResource(R.color.true_atende_final_de_semana)
                    ),
                    modifier = Modifier
                        .scale(0.7f)
                        .offset(y = -18.dp)
                    ,
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                shape = Shapes().small,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer))
            ) {
                Text(
                    text = "Criar",
                    fontSize = 15.sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
private fun CadastroOngScreenPreview() {
    CadastroOngScreen()
}
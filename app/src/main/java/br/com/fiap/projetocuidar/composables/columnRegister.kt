package br.com.fiap.projetocuidar.composables

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun ColumnRegister(modifier: Modifier = Modifier) {
    val senha = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val nome = remember { mutableStateOf("") }
    val sobrenome = remember { mutableStateOf("") }
    val telefone = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()
        .background(color = colorResource(R.color.cor_column_registre)

        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(color = colorResource(R.color.white))
                .offset(y = 10.dp),
                horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Login",
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                color = colorResource(R.color.cor_text_login),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                fontSize = 17.sp
                )
            Icon(
                painter = painterResource(R.drawable.seta_esquerda_back_24),
                contentDescription = "Seta para esquerda",
                modifier = Modifier
                    .size(25.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .offset(x = -180.dp),
                tint = colorResource(R.color.cor_registre)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(720.dp)
                .offset(y= 100.dp)
                .background(color= colorResource(R.color.cor_column_registre))
        ) {
            Text(
                "Crie sua conta",
                fontSize = 30.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                modifier = Modifier.padding(top = 10.dp, start = 30.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier
                    .padding( start = 30.dp, end = 60.dp)
                    .width(300.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Email",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(10.dp)
                    .offset(x = 20.dp),
                placeholder = {
                    Text(text = "EX: fulano@beltrano.com ")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Nome",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = nome.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(10.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                "Sobrenome",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = sobrenome.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(10.dp)
                    .offset(x = 20.dp),
                placeholder = {
                    Text(text = "EX: Andrade")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Telefone",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = telefone.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(10.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Senha",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = senha.value,
                onValueChange = { "" },
                modifier = Modifier
                    .width(320.dp)
                    .size(40.dp)
                    .padding(10.dp)
                    .offset(x = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .offset(y = 190.dp, x = 55.dp)
                    .width(280.dp),
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
            Text(
                text = "Já tem uma conta?",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.offset(x = 15.dp, y = -28.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColumnRegisterPreview() {
    ColumnRegister()
}
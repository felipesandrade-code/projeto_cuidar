package br.com.fiap.projetocuidar.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun ColumnRegister(modifier: Modifier = Modifier) {
    val senha = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.cor_card_footer)),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .offset(y = -80.dp)
                .align(alignment = Alignment.Center)
        ) {
            Text(
                "Login",
                fontSize = 20.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { "" },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Digite o seu e-mail",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                },
                placeholder = {
                    Text(text = "Digite o seu e-mail aqui EX: john.marshall.harlan@examplepetstore.com")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Text(
                text = "Senha",
                fontSize = 20.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                modifier = Modifier.offset(y = 30.dp)
            )
            OutlinedTextField(
                value = senha.value,
                onValueChange = { "" },
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 30.dp),
                label = {
                    Text(text = "Digite a sua senha")
                },
                placeholder = {
                    Text(text = "Digite a sua senha aqui")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .offset(y = 140.dp)
                    .background(color = colorResource(R.color.cor_botao)),
                shape = Shapes().large
            ) {
                Text(
                    text = "Cadastrar",
                    fontSize = 15.sp,
                    color = colorResource(R.color.black),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    modifier = Modifier.padding(horizontal = 50.dp)
                )
            }
            Text(
                text = "Já tem uma conta?",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}
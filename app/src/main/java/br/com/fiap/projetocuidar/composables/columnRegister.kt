package br.com.fiap.projetocuidar.composables

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
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
    val numero = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
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
                .offset(y = 10.dp)
                .align(alignment = Alignment.Center)
        ) {
            Text(
                "Registre-se aqui",
                fontSize = 20.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Nome",
                fontSize = 20.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = nome.value,
                onValueChange = { "" },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp),
                label = {
                    Text(
                        text = "Digite o seu nome",
                    )
                },
                placeholder = {
                    Text(text = "EX: Fulano ")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                "Sobrenome",
                fontSize = 20.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = sobrenome.value,
                onValueChange = { "" },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Digite o seu sobrenome",
                    )
                },
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
                "Número de telefone",
                fontSize = 20.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
            OutlinedTextField(
                value = numero.value,
                onValueChange = { "" },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Digite o seu Número de telefone",
                    )
                },
                placeholder = {
                    Text(text = "EX: (11) 99999-999")
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
                modifier = Modifier.offset(y = 20.dp)
            )
            OutlinedTextField(
                value = senha.value,
                onValueChange = { "" },
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 20.dp),
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
                    .offset(y = 100.dp)
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
                modifier = Modifier.offset(y = -10.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColumnRegisterPreview() {
    ColumnRegister()
}
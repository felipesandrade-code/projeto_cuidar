package br.com.fiap.projetocuidar.components.registroUsuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.ValidateEmail
import br.com.fiap.projetocuidar.components.ButtonsComponent
import br.com.fiap.projetocuidar.components.CaixaDeEntradaComponent
import br.com.fiap.projetocuidar.components.DividerComponent
import br.com.fiap.projetocuidar.components.SuperiorRegister
import br.com.fiap.projetocuidar.components.TextClickable
import br.com.fiap.projetocuidar.components.TextRegisterUsuario
import br.com.fiap.projetocuidar.components.TituloComponents
import br.com.fiap.projetocuidar.validateSenha

@Composable
fun RegisterComponents(modifier: Modifier = Modifier, navcontroller: NavController) {
    var senha by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var nomeErrorMessage by remember { mutableStateOf<String?>(null) }
    var sobrenomeErrorMessage by remember { mutableStateOf<String?>(null) }
    var telefoneErrorMessage by remember { mutableStateOf<String?>(null) }
    var senhaErrorMessage by remember { mutableStateOf<String?>(null) }

    val validarEmail = ValidateEmail(email)
    val validarSenha = validateSenha(senha)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    )
    {
        SuperiorRegister(Modifier,stringResource(R.string.text_login), navcontroller)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 75.dp)
                .padding(15.dp)
        ) {
            TituloComponents(Modifier, "Crie sua conta", 30.sp)
            DividerComponent()

            Spacer(modifier = Modifier.height(10.dp))
            TextRegisterUsuario(stringResource(R.string.text_email))
            Spacer(modifier = Modifier.height(5.dp))
            CaixaDeEntradaComponent(
                Modifier,
                value = email,
                onvalueChange = { email = it },
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 300.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 30.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 46.dp,
                singleLine = true,
                isError = false
            )
            emailErrorMessage?.let { msg ->
                Text(
                    text = "Digite um e-mail válido",
                    color = Color.Red,
                    fontSize = 11.sp,
                    modifier = Modifier.offset(x = 30.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            TextRegisterUsuario(stringResource(R.string.text_senha))
            Spacer(modifier = Modifier.height(5.dp))
            CaixaDeEntradaComponent(
                Modifier,
                value = senha,
                onvalueChange = { senha = it },
                keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.None,
                caixaDeEntradaWidth = 300.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 30.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 46.dp,
                singleLine = true,
                isError = false
            )
            senhaErrorMessage?.let { msg ->
                Text(
                    text = "senha inválida, digite novamente.",
                    color = Color.Red,
                    fontSize = 11.sp,
                    modifier = Modifier.offset(x = 30.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextRegisterUsuario(stringResource(R.string.text_name))
            Spacer(modifier = Modifier.height(5.dp))
            CaixaDeEntradaComponent(
                Modifier,
                value = nome,
                onvalueChange = { nome = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 300.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 30.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 46.dp,
                singleLine = true,
                isError = false
            )
            nomeErrorMessage?.let {
                Text(
                    "Campo Nome vazio, por favor digite um nome",
                    color = Color.Red,
                    fontSize = 11.sp,
                    modifier = Modifier.offset(x = 30.dp)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            TextRegisterUsuario(stringResource(R.string.text_surname))
            Spacer(modifier = Modifier.height(5.dp))
            CaixaDeEntradaComponent(
                Modifier,
                value = sobrenome,
                onvalueChange = { sobrenome = it},
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 300.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 30.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 46.dp,
                singleLine = true,
                isError = false
            )
            sobrenomeErrorMessage?.let {
                Text(
                    "Campo sobrenome está vazio, por favor digite o seu sobrenome.",
                    color = Color.Red,
                    fontSize = 11.sp,
                    modifier = Modifier.offset(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextRegisterUsuario(stringResource(R.string.text_telefone))
            Spacer(modifier = Modifier.height(5.dp))
            CaixaDeEntradaComponent(
                Modifier,
                value = telefone,
                onvalueChange = { telefone = it },
                keyboardType = KeyboardType.Number,
                capitalization = KeyboardCapitalization.Unspecified,
                caixaDeEntradaWidth = 300.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 30.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 46.dp,
                singleLine = true,
                isError = false
            )
            telefoneErrorMessage?.let {
                Text(
                    "Campo telefone vazio, por favor digite um telefone",
                    color = Color.Red,
                    fontSize = 11.sp,
                    modifier = Modifier.offset(x = 30.dp)

                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(8.dp))
            TextClickable(
                text = "Já tem uma conta?",
                navcontroller = navcontroller,
                route = "login",
                fontSize = 14.sp,
                offsetX = 40.dp,
                offsetY = 0.dp
            )
            ButtonsComponent(
                modifier = Modifier,
                buttonwidth = 280.dp,
                buttonOffsetX = 45.dp,
                buttonOffsetY = 20.dp,
                onClick = {
                    sobrenomeErrorMessage = if(sobrenome.isBlank()) "Digite um sobrenome por favor " else null
                    nomeErrorMessage = if(nome.isBlank()) "Digite um nome por favor " else null
                    telefoneErrorMessage = if(telefone.isBlank()) "Digite um telefone por favor " else null
                    emailErrorMessage = if(email.isBlank()) "Digite um e-mail por favor " else validarEmail
                    senhaErrorMessage = if(senha.isBlank()) "Digite uma senha por favor " else validarSenha

                    if(listOf(nomeErrorMessage, emailErrorMessage, senhaErrorMessage, sobrenomeErrorMessage, telefoneErrorMessage).all { it == null })
                    navcontroller.navigate("login"){
                    }
                },
                text = stringResource(R.string.text_cadastrar),
                singleLine = true
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColumnRegisterPreview() {
    RegisterComponents(navcontroller = rememberNavController())
}
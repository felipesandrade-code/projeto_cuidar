package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import br.com.fiap.projetocuidar.components.CaixaDeEntradaEmail
import br.com.fiap.projetocuidar.components.CaixaDeEntradaSenha
import br.com.fiap.projetocuidar.components.IconesLogin
import br.com.fiap.projetocuidar.components.ImgComponent
import br.com.fiap.projetocuidar.components.LogoComponent
import br.com.fiap.projetocuidar.components.TextClickable
import br.com.fiap.projetocuidar.components.TextsLogin
import br.com.fiap.projetocuidar.validateSenha


@Composable
fun LoginComponents(
    navController: NavController,
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    val senhaErrorValidate = validateSenha(senha)
    val emailErrorValidate = ValidateEmail(email)
    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var senhaErrorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.white))
    )
    {
        Row(modifier = Modifier.fillMaxWidth()) {
            ImgComponent()
        }
        Column(
            modifier = Modifier
                .offset(y = 240.dp)
                .fillMaxWidth()
                .height(650.dp)
                .background(
                    color = colorResource(R.color.cor_column_registre),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
        ) {
            LogoComponent("Logo", logoSize = 130.dp, logoOffsetX = 10.dp, logoOffsetY = 10.dp)
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextsLogin(Modifier, stringResource(R.string.text_email))
                Spacer(modifier = Modifier.height(5.dp))
                CaixaDeEntradaEmail(
                    modifier = Modifier,
                    value = email,
                    onvalueChange = { email = it },
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None,
                    singleLine = true,
                    caixaDeEntradaWidth = 310.dp,
                    caixaDeEntradaPaddingStart = 0.dp,
                    caixaDeEntradaPaddingTop = 0.dp,
                    caixaDeEntradaOffsetX = 40.dp,
                    caixaDeEntradaOffsetY = 0.dp,
                    caixaDeEntradaSize = 56.dp,
                    isError = emailErrorMessage != null
                )
                emailErrorMessage?.let { msg ->
                    Text(
                        text = "Digite um e-mail válido",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 40.dp, top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextsLogin(Modifier, stringResource(R.string.text_senha))
                Spacer(modifier = Modifier.height(5.dp))
                CaixaDeEntradaSenha(
                    modifier = Modifier,
                    value = senha,
                    onvalueChange = { senha = it },
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None,
                    singleLine = true,
                    caixaDeEntradaWidth = 310.dp,
                    caixaDeEntradaPaddingStart = 0.dp,
                    caixaDeEntradaPaddingTop = 0.dp,
                    caixaDeEntradaOffsetX = 40.dp,
                    caixaDeEntradaOffsetY = 0.dp,
                    caixaDeEntradaSize = 56.dp,
                    isPassword = true
                )
                senhaErrorMessage?.let { msg ->
                    Text(
                        text = "senha inválida, digite novamente.",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 40.dp, top = 4.dp)
                    )
                }
                TextClickable(
                    Modifier,
                    "Ainda não tem uma conta?",
                    route = "Registro",
                    navcontroller = navController,
                    fontSize = 13.sp,
                    offsetX = 30.dp,
                    offsetY = 5.dp
                )
                ButtonsComponent(
                    modifier = Modifier,
                    buttonwidth = 280.dp,
                    buttonOffsetX = 55.dp,
                    buttonOffsetY = 30.dp,
                    onClick = {
                        emailErrorMessage = emailErrorValidate
                        senhaErrorMessage = senhaErrorValidate
                        if (emailErrorValidate == null && senhaErrorValidate == null) {
                            navController.navigate("home")
                        }
                    },
                    text = stringResource(R.string.text_login),
                    singleLine = true
                )
                IconesLogin()
            }
        }
    }
}
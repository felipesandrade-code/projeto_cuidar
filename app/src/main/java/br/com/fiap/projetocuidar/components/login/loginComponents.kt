package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.ValidateEmail
import br.com.fiap.projetocuidar.components.FormFieldLabel
import br.com.fiap.projetocuidar.components.LogoComponent
import br.com.fiap.projetocuidar.components.PrimaryButton
import br.com.fiap.projetocuidar.data.AuthState
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.validateSenha

@Composable
fun LoginComponents(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaOculta by remember { mutableStateOf(true) }
    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var senhaErrorMessage by remember { mutableStateOf<String?>(null) }
    var apiErrorMessage by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is AuthState.Loading

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                navController.navigate(state.navigateTo) {
                    popUpTo("login") { inclusive = true }
                }
                authViewModel.resetAuthState()
            }
            is AuthState.Error -> {
                apiErrorMessage = state.message
                authViewModel.resetAuthState()
            }
            else -> {}
        }
    }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = colorResource(R.color.cor_registre),
        unfocusedBorderColor = Color.LightGray
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Image(
            painter = painterResource(R.drawable.kids),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(colorResource(R.color.cor_column_registre))
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogoComponent("Logo", 56.dp, 0.dp, 0.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cuidar+",
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    color = colorResource(R.color.cor_registre)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            apiErrorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            FormFieldLabel("Email")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; apiErrorMessage = null },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o seu e-mail", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None
                ),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = emailErrorMessage != null,
                enabled = !isLoading
            )
            emailErrorMessage?.let {
                Text("Digite um e-mail válido", color = Color.Red, fontSize = 12.sp,
                    modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Senha")
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it; apiErrorMessage = null },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite a sua senha", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None
                ),
                visualTransformation = if (senhaOculta) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { senhaOculta = !senhaOculta }) {
                        Icon(
                            imageVector = if (senhaOculta) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = colorResource(R.color.cor_text_login)
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = senhaErrorMessage != null,
                enabled = !isLoading
            )
            senhaErrorMessage?.let {
                Text("Senha inválida, digite novamente.", color = Color.Red, fontSize = 12.sp,
                    modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = colorResource(R.color.cor_registre),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )
            }

            PrimaryButton(
                text = "Entrar",
                onClick = {
                    val emailError = ValidateEmail(email)
                    val senhaError = validateSenha(senha)
                    emailErrorMessage = emailError
                    senhaErrorMessage = senhaError
                    if (emailError == null && senhaError == null) {
                        authViewModel.login(email, senha)
                    }
                },
                modifier = Modifier.then(if (isLoading) Modifier else Modifier)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Não tem conta? ",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = colorResource(R.color.cor_text_login)
                )
                Text(
                    text = "Crie aqui",
                    fontSize = 13.sp,
                    color = colorResource(R.color.cor_registre),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(enabled = !isLoading) {
                        navController.navigate("registro")
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

package br.com.fiap.projetocuidar.components.registroUsuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.ValidateEmail
import br.com.fiap.projetocuidar.components.DividerComponent
import br.com.fiap.projetocuidar.components.FormFieldLabel
import br.com.fiap.projetocuidar.components.PrimaryButton
import br.com.fiap.projetocuidar.components.SuperiorRegister
import br.com.fiap.projetocuidar.components.TituloComponents
import br.com.fiap.projetocuidar.data.AuthState
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.User
import br.com.fiap.projetocuidar.util.CpfCnpjVisualTransformation
import br.com.fiap.projetocuidar.util.TelefoneVisualTransformation
import br.com.fiap.projetocuidar.validateSenha
import br.com.fiap.projetocuidar.validateTelefone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterComponents(
    modifier: Modifier = Modifier,
    navcontroller: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cpfCnpj by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("") }
    var tipoExpanded by remember { mutableStateOf(false) }

    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var nomeErrorMessage by remember { mutableStateOf<String?>(null) }
    var telefoneErrorMessage by remember { mutableStateOf<String?>(null) }
    var senhaErrorMessage by remember { mutableStateOf<String?>(null) }
    var cpfCnpjErrorMessage by remember { mutableStateOf<String?>(null) }
    var tipoErrorMessage by remember { mutableStateOf<String?>(null) }
    var registrationErrorMessage by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is AuthState.Loading

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                navcontroller.navigate(state.navigateTo) {
                    popUpTo("registro") { inclusive = true }
                }
                authViewModel.resetAuthState()
            }
            is AuthState.Error -> {
                registrationErrorMessage = state.message
                authViewModel.resetAuthState()
            }
            else -> {}
        }
    }

    val validarEmail = ValidateEmail(email)
    val validarSenha = validateSenha(senha)
    val validarTelefone = validateTelefone(telefone)

    val tipoOptions = listOf("Voluntário", "Doador", "Orfanato")

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = colorResource(R.color.cor_registre),
        unfocusedBorderColor = Color.LightGray
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.cor_column_registre))
    ) {
        SuperiorRegister(Modifier, "Cadastro", navcontroller)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TituloComponents(Modifier, "Crie sua conta", 30.sp)
            DividerComponent()
            Spacer(modifier = Modifier.height(16.dp))

            registrationErrorMessage?.let { msg ->
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
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o seu e-mail", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, capitalization = KeyboardCapitalization.None),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = emailErrorMessage != null
            )
            emailErrorMessage?.let {
                Text(it, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Nome")
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o seu nome", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = nomeErrorMessage != null
            )
            nomeErrorMessage?.let {
                Text(it, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Telefone")
            OutlinedTextField(
                value = telefone,
                onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 11) telefone = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o seu telefone com o DDD", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = telefoneErrorMessage != null,
                visualTransformation = TelefoneVisualTransformation()
            )
            telefoneErrorMessage?.let {
                Text(it, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Senha")
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite a sua senha", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, capitalization = KeyboardCapitalization.None),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = senhaErrorMessage != null
            )
            senhaErrorMessage?.let {
                Text(it, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("CPF/CNPJ")
            OutlinedTextField(
                value = cpfCnpj,
                onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 14) cpfCnpj = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o seu cpf/cnpj", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = cpfCnpjErrorMessage != null,
                visualTransformation = CpfCnpjVisualTransformation()
            )
            cpfCnpjErrorMessage?.let {
                Text(it, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Tipo de usuário")
            ExposedDropdownMenuBox(
                expanded = tipoExpanded,
                onExpandedChange = { tipoExpanded = !tipoExpanded },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = tipoUsuario,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpanded) },
                    placeholder = { Text("Selecione se você é voluntário, doador ou orfanato", color = Color.Gray, fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors,
                    isError = tipoErrorMessage != null
                )
                ExposedDropdownMenu(
                    expanded = tipoExpanded,
                    onDismissRequest = { tipoExpanded = false }
                ) {
                    tipoOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                            onClick = {
                                tipoUsuario = option
                                tipoExpanded = false
                            }
                        )
                    }
                }
            }
            tipoErrorMessage?.let {
                Text(it, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = if (isLoading) "Criando..." else "Criar",
                onClick = {
                    nomeErrorMessage = if (nome.isBlank()) "Digite um nome" else null
                    telefoneErrorMessage = validarTelefone
                    emailErrorMessage = if (email.isBlank()) "Digite um e-mail" else validarEmail
                    senhaErrorMessage = validarSenha
                    cpfCnpjErrorMessage = if (cpfCnpj.isBlank()) "Digite o CPF/CNPJ" else null
                    tipoErrorMessage = if (tipoUsuario.isBlank()) "Selecione o tipo de usuário" else null

                    if (listOf(nomeErrorMessage, emailErrorMessage, senhaErrorMessage, telefoneErrorMessage, cpfCnpjErrorMessage, tipoErrorMessage).all { it == null }) {
                        authViewModel.register(
                            User(
                                email = email,
                                senha = senha,
                                nome = nome,
                                telefone = telefone,
                                cpfCnpj = cpfCnpj,
                                tipoUsuario = tipoUsuario
                            )
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


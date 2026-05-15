package br.com.fiap.projetocuidar.components.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.FormFieldLabel
import br.com.fiap.projetocuidar.components.SuperiorComLogo
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.User

@Composable
fun PerfilComponents(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    var nome by remember { mutableStateOf(currentUser?.nome ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var cpfCnpj by remember { mutableStateOf(currentUser?.cpfCnpj ?: "") }
    var senha by remember { mutableStateOf(currentUser?.senha ?: "") }

    var saved by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = colorResource(R.color.cor_registre),
        unfocusedBorderColor = Color.LightGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.cor_column_registre))
            .verticalScroll(rememberScrollState())
    ) {
        SuperiorComLogo(navcontroller = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Meu perfil",
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            color = colorResource(R.color.cor_registre),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Foto de perfil placeholder
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.cor_column_registre))
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Foto de perfil",
                modifier = Modifier.fillMaxSize(),
                tint = colorResource(R.color.cor_registre)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        FormFieldLabel("Nome")
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("E-mail")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, capitalization = KeyboardCapitalization.None),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("CPF/CNPJ")
        OutlinedTextField(
            value = cpfCnpj,
            onValueChange = { cpfCnpj = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("Senha")
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (saved) {
            Text(
                text = "Alterações salvas!",
                color = colorResource(R.color.cor_registre),
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
            )
        }

        // Botão "Salvar alterações" — estilo outline (ghost)
        OutlinedButton(
            onClick = {
                currentUser?.let { user ->
                    authViewModel.updateUser(
                        User(
                            email = email,
                            senha = senha,
                            nome = nome,
                            sobrenome = user.sobrenome,
                            telefone = user.telefone,
                            cpfCnpj = cpfCnpj,
                            tipoUsuario = user.tipoUsuario
                        )
                    )
                    saved = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(R.color.cor_registre)
            )
        ) {
            Text(
                text = "Salvar alterações",
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_registre)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

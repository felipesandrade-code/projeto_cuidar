package br.com.fiap.projetocuidar.components.registroUsuario

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun RegisterComponents(modifier: Modifier = Modifier) {
    val senha = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val nome = remember { mutableStateOf("") }
    val sobrenome = remember { mutableStateOf("") }
    val telefone = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    )
    {
        SuperiorRegister()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 110.dp)
        ) {
            Text(
                "Crie sua conta",
                fontSize = 30.sp,
                color = colorResource(R.color.cor_registre),
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 30.dp, end = 60.dp)
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
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = email.value,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Email,
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
            CaixaDeEntradaString(
                modifier = Modifier,
                value = nome.value,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
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
            CaixaDeEntradaString(
                modifier = Modifier,
                value = sobrenome.value,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
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
            CaixaDeEntradaTelefone(
                modifier = Modifier,
                value = telefone.value,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Phone,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
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
            CaixaDeEntradaSenha(
                modifier = Modifier,
                value = senha.value,
                onvalueChange = {""},
                keyboardType = KeyboardType.Password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.Words
                ),
                shape = Shapes().medium
            )
            ButtonRegistro()
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
    RegisterComponents()
}
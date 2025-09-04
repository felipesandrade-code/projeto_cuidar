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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.cadastroOng.CaixasDeEntradaTextComponents
import br.com.fiap.projetocuidar.components.cadastroOng.DividerComponent
import br.com.fiap.projetocuidar.components.cadastroOng.TituloComponents

@Composable
fun RegisterComponents(modifier: Modifier = Modifier) {
    var senha by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    )
    {
        SuperiorRegister(Modifier, stringResource(R.string.text_login))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 110.dp)
        ) {
            TituloComponents(Modifier, "Crie sua conta", 30.sp)
            Spacer(modifier = Modifier.height(10.dp))
            DividerComponent()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.text_email),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CaixasDeEntradaTextComponents(
                value = email,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                stringResource(R.string.text_name),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CaixasDeEntradaTextComponents(
                value = nome,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                stringResource(R.string.text_surname),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CaixasDeEntradaTextComponents(
                    value = sobrenome,
                    onvalueChange = { "" },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    ),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                stringResource(R.string.text_telefone),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CaixasDeEntradaTextComponents(
                value = telefone,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.text_senha),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CaixasDeEntradaTextComponents(
                value = senha,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.Words
                ),
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
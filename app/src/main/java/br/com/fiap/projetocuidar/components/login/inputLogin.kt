package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.ButtonsComponent
import br.com.fiap.projetocuidar.components.CaixaDeEntradaComponent

@Composable
fun InputLogin(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .offset(y = 20.dp)
            .fillMaxWidth()
    ) {
        TextsLogin(Modifier, stringResource(R.string.text_email))
        Spacer(modifier = Modifier.height(5.dp))
        CaixaDeEntradaComponent(
            modifier = Modifier,
            value = "",
            onvalueChange = {},
            keyboardType = KeyboardType.Password,
            capitalization = KeyboardCapitalization.None,
            singleLine = true,
            caixaDeEntradaWidth = 310.dp,
            caixaDeEntradaPaddingStart = 0.dp,
            caixaDeEntradaPaddingTop = 0.dp,
            caixaDeEntradaOffsetX = 40.dp,
            caixaDeEntradaOffsetY = 0.dp,
            caixaDeEntradaSize = 30.dp
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextsLogin(Modifier, stringResource(R.string.text_senha))
        Spacer(modifier = Modifier.height(5.dp))
        CaixaDeEntradaComponent(
            modifier = Modifier,
            value = "",
            onvalueChange = {},
            keyboardType = KeyboardType.Password,
            capitalization = KeyboardCapitalization.None,
            singleLine = true,
            caixaDeEntradaWidth = 310.dp,
            caixaDeEntradaPaddingStart = 0.dp,
            caixaDeEntradaPaddingTop = 0.dp,
            caixaDeEntradaOffsetX = 40.dp,
            caixaDeEntradaOffsetY = 0.dp,
            caixaDeEntradaSize = 30.dp
        )
        ButtonsComponent(
            modifier = Modifier,
            buttonwidth = 280.dp,
            buttonOffsetX = 55.dp,
            buttonOffsetY = 20.dp,
            onClick = { navController.navigate("register") },
            text = stringResource(R.string.text_login)
        )
        IconesLogin()
    }
}
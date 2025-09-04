package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R

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
        CaixasDeEntradaLogin()
        Spacer(modifier = Modifier.height(20.dp))
        TextsLogin(Modifier, stringResource(R.string.text_senha))
        Spacer(modifier = Modifier.height(5.dp))
        CaixasDeEntradaLogin()
        BotaoLogin(onClick = { navController.navigate("home")})
        IconesLogin()
    }
}
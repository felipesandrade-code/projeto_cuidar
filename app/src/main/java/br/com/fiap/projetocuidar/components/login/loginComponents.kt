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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.R


@Composable
fun LoginComponents(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.white))
    )
    {
        Row(modifier = Modifier.fillMaxWidth()) {
            ImagemLogin()
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
            LogoLogin(Modifier)
            Spacer(modifier = Modifier.height(40.dp))
            InputLogin(Modifier, navController)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginComponentsPreview() {
    val nav = rememberNavController()
    LoginComponents(navController = nav)
}
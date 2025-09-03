package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo cuidar+",
                modifier = Modifier
                    .size(140.dp)
                    .offset(y = 20.dp, x = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .offset(y = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Email",
                    fontSize = 15.sp,
                    color = colorResource(R.color.cor_text_login),
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    modifier = Modifier.offset(x = 30.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                CaixasDeEntradaLogin()
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Senha",
                    fontSize = 15.sp,
                    color = colorResource(R.color.cor_text_login),
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    modifier = Modifier.offset(x = 30.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                CaixasDeEntradaLogin()
                BotaoLogin()
                IconesLogin()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginComponentsPreview() {
    val nav = rememberNavController()
    LoginComponents(navController = nav)
}
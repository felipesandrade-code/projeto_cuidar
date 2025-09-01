package br.com.fiap.projetocuidar.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R

@Composable
fun ColumnLogin(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo cuidar+",
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
                .offset(y = 50.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .offset(y = 40.dp)
        ){
            Button(
                onClick = {/*TODO*/ },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(width = 150.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.cor_botao)
                )
            ) {
                Text(
                    text = stringResource(R.string.botao_login),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))

                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Registre-se aqui",
                color = colorResource(R.color.cor_registre),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        }
    }
    Box(modifier = Modifier.absoluteOffset(y= 700.dp)) {
        CardFooter()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColumnLoginPreview() {
    ColumnLogin()
}
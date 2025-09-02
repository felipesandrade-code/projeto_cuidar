package br.com.fiap.projetocuidar.composables

import android.graphics.Color
import android.icu.text.ListFormatter
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.R



@Composable
fun ColumnLogin(
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.kids_happy),
                contentDescription = "Crianças abraçadas",
                modifier = Modifier
                    .size(700.dp)
                    .offset(y = -150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .drawWithContent{
                        drawContent()

                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    androidx.compose.ui.graphics.Color.Black.copy( 0.5f),
                                    androidx.compose.ui.graphics.Color.Black.copy(0.5f)
                                ),
                                startY = 0f,
                                endY = size.height / 3
                            ),
                            size = size
                        )
                    }
            )
        }
        Column(
            modifier = Modifier
                .offset(y = 270.dp)
                .fillMaxWidth()
                .height(650.dp)
                .background(color = colorResource(R.color.cor_column_registre),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo cuidar+",
                modifier = Modifier
                    .size(140.dp)
                    .offset(y= 20.dp, x= 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .offset(y = 20.dp,)
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
                OutlinedTextField(
                    value = "",
                    onValueChange = {""},
                    shape = Shapes().medium,
                    modifier = Modifier
                        .offset(x = 40.dp)
                        .width(310.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        colorResource(R.color.black),
                        unfocusedContainerColor = colorResource(R.color.white),
                        unfocusedBorderColor = colorResource(R.color.cor_card_footer),
                        disabledContainerColor = colorResource(R.color.white),
                        cursorColor = colorResource(R.color.black)),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                    keyboardActions = KeyboardActions()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Senha",
                    fontSize = 15.sp,
                    color = colorResource(R.color.cor_text_login),
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    modifier = Modifier.offset(x = 30.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {""},
                    shape = Shapes().medium,
                    modifier = Modifier
                        .offset(x = 40.dp)
                        .width(310.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        colorResource(R.color.black),
                        unfocusedContainerColor = colorResource(R.color.white),
                        unfocusedBorderColor = colorResource(R.color.cor_card_footer),
                        disabledContainerColor = colorResource(R.color.white),
                        cursorColor = colorResource(R.color.black)),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                    keyboardActions = KeyboardActions()
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(280.dp)
                        .offset(y = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = Shapes().small,
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer))
                ) {
                    Text(
                        text = "Entrar",
                        fontSize = 15.sp,
                        color = colorResource(R.color.white),
                        fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        painter = painterResource(R.drawable.icons_facebook),
                        contentDescription = "Logo Facebook",
                        tint = colorResource(R.color.cor_registre),
                        modifier = Modifier
                            .offset(y = 50.dp)
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(35.dp))
                    Icon(
                        painter = painterResource(R.drawable.icons_google),
                        contentDescription = "Logo google",
                        tint = colorResource(R.color.cor_registre),
                        modifier = Modifier
                            .size(30.dp)
                            .offset(y = 50.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColumnLoginPreview() {
    val nav = rememberNavController()
    ColumnLogin(navController = nav)
}
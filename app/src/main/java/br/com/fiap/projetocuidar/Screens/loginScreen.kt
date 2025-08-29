package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R

@Composable
fun LoginScreen(navController: NavController.Companion, modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(32.dp)
    ){
        Image(
            painter = painterResource(R.drawable.logo,),
            contentDescription = "Logo cuidar+",
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
                .align(alignment = Alignment.TopCenter)
        )
        Button(
            onClick = {/*TODO*/},
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.cor_botao)
            )
        ){

        }
    }
}
package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.composables.CardFooter
import br.com.fiap.projetocuidar.composables.ColumnLogin

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(32.dp)
    ){
        ColumnLogin()
    }
    Box(modifier = Modifier.absoluteOffset(y= 650.dp)){
        CardFooter()
    }
}
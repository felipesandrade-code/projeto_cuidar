package br.com.fiap.projetocuidar.Screens

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(end = 30.dp)
                    .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
                    .size(40.dp),
                colorResource(R.color.cor_registre)
            )
        }
        Box(modifier = Modifier
            .offset(y = 180.dp, x = 40.dp)
            .border(border = BorderStroke(1.dp, color = colorResource(R.color.cor_card_footer))),
        ){
            Column(
                modifier = Modifier
                    .background(color = colorResource(R.color.cor_card_footer))
                    .align(alignment = androidx.compose.ui.Alignment.Center)
                    .border(border = BorderStroke(1.dp, color = colorResource(R.color.cor_card_footer))),
            ) {
                Text(text = "Mapa Ongs")
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
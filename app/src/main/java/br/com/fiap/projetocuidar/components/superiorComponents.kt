package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R

@Composable
fun SuperiorCadastroOng(
    modifier: Modifier = Modifier,
    text: String,
    navcontroller: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = colorResource(R.color.white))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navcontroller.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = colorResource(R.color.cor_registre),
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            fontSize = 17.sp
        )
        IconButton(onClick = { navcontroller.navigate("mapa") }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Fechar",
                tint = colorResource(R.color.cor_text_login),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun SuperiorOngComponent(
    modifier: Modifier = Modifier,
    text: String,
    navcontroller: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = colorResource(R.color.white))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navcontroller.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = colorResource(R.color.cor_registre),
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun SuperiorRegister(
    modifier: Modifier = Modifier,
    text: String,
    navcontroller: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = colorResource(R.color.white))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navcontroller.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = colorResource(R.color.cor_registre),
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            fontSize = 17.sp
        )
        IconButton(onClick = { navcontroller.navigate("login") }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Fechar",
                tint = colorResource(R.color.cor_text_login),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun SuperiorMapa(navcontroller: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = colorResource(R.color.white))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navcontroller.navigate("home") { launchSingleTop = true } }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = colorResource(R.color.cor_registre),
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = "ONGs próximas",
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            color = colorResource(R.color.cor_text_login),
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
fun SuperiorComLogo(
    navcontroller: NavController,
    closeRoute: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = colorResource(R.color.white))
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navcontroller.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = colorResource(R.color.cor_registre),
                modifier = Modifier.size(22.dp)
            )
        }
        LogoComponent("Cuidar+", 48.dp, 0.dp, 0.dp)
        if (closeRoute != null) {
            IconButton(onClick = { navcontroller.navigate(closeRoute) }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Fechar",
                    tint = colorResource(R.color.cor_text_login),
                    modifier = Modifier.size(22.dp)
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

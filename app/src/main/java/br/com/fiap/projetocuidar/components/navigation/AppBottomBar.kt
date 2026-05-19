package br.com.fiap.projetocuidar.components.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.AuthViewModel

@Composable
fun AppBottomBar(
    navController: NavController,
    authViewModel: AuthViewModel,
    currentRoute: String
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val tipoUsuario = currentUser?.tipoUsuario?.lowercase() ?: ""
    val isVoluntario = tipoUsuario.contains("voluntário") || tipoUsuario.contains("voluntario")
    val isOrfanato = tipoUsuario.contains("orfanato") ||
        tipoUsuario.contains("ong") ||
        tipoUsuario.contains("operator") ||
        tipoUsuario.contains("admin")

    val verdePrimario = colorResource(R.color.cor_registre)
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = verdePrimario,
        unselectedIconColor = colorResource(R.color.cor_text_login),
        selectedTextColor = verdePrimario,
        unselectedTextColor = colorResource(R.color.cor_text_login),
        indicatorColor = colorResource(R.color.cor_column_registre)
    )

    fun navigateTo(route: String) {
        if (currentRoute == route) return
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo("home") { saveState = true }
        }
    }

    val messageRoute = if (isOrfanato) "operator_messages" else "chat_inbox"

    NavigationBar(
        containerColor = colorResource(R.color.white),
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { navigateTo("home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", modifier = Modifier.size(24.dp)) },
            label = { Text("Home", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
            colors = itemColors
        )
        NavigationBarItem(
            selected = currentRoute == "perfil",
            onClick = { navigateTo("perfil") },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil", modifier = Modifier.size(24.dp)) },
            label = { Text("Perfil", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
            colors = itemColors
        )
        if (!isOrfanato) {
            NavigationBarItem(
                selected = currentRoute == "mapa",
                onClick = { navigateTo("mapa") },
                icon = { Icon(Icons.Filled.Map, contentDescription = "Mapa", modifier = Modifier.size(24.dp)) },
                label = { Text("Mapa", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                colors = itemColors
            )
        }
        if (!isVoluntario && !isOrfanato) {
            NavigationBarItem(
                selected = currentRoute == "doacao",
                onClick = { navigateTo("doacao") },
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Doação", modifier = Modifier.size(24.dp)) },
                label = { Text("Doação", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                colors = itemColors
            )
        }
        NavigationBarItem(
            selected = currentRoute == messageRoute,
            onClick = { navigateTo(messageRoute) },
            icon = { Icon(Icons.Filled.Mail, contentDescription = "Mensagens", modifier = Modifier.size(24.dp)) },
            label = { Text("Mensagens", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
            colors = itemColors
        )
    }
}

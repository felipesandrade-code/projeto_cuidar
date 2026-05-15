package br.com.fiap.projetocuidar.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.LogoComponent
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@Composable
fun HomeComponents(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    val tipoUsuario = currentUser?.tipoUsuario?.lowercase() ?: ""
    val isVoluntario = tipoUsuario == "voluntário"

    val verdePrimario = colorResource(R.color.cor_registre)
    val verdeSecundario = colorResource(R.color.cor_card_footer)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.white),
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate("home") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home", modifier = Modifier.size(24.dp)) },
                    label = { Text("Home", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = verdePrimario,
                        selectedTextColor = verdePrimario,
                        indicatorColor = colorResource(R.color.cor_column_registre)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("perfil") },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil", modifier = Modifier.size(24.dp)) },
                    label = { Text("Perfil", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = verdePrimario,
                        unselectedIconColor = colorResource(R.color.cor_text_login),
                        selectedTextColor = verdePrimario,
                        unselectedTextColor = colorResource(R.color.cor_text_login),
                        indicatorColor = colorResource(R.color.cor_column_registre)
                    )
                )
                if (isVoluntario) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("mapa") },
                        icon = { Icon(Icons.Filled.Map, contentDescription = "Mapa", modifier = Modifier.size(24.dp)) },
                        label = { Text("Mapa", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = verdePrimario,
                            unselectedIconColor = colorResource(R.color.cor_text_login),
                            selectedTextColor = verdePrimario,
                            unselectedTextColor = colorResource(R.color.cor_text_login),
                            indicatorColor = colorResource(R.color.cor_column_registre)
                        )
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("doacao") },
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Doação", modifier = Modifier.size(24.dp)) },
                        label = { Text("Doação", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = verdePrimario,
                            unselectedIconColor = colorResource(R.color.cor_text_login),
                            selectedTextColor = verdePrimario,
                            unselectedTextColor = colorResource(R.color.cor_text_login),
                            indicatorColor = colorResource(R.color.cor_column_registre)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Topbar: logo + hamburger
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogoComponent("Logo", 100.dp, 0.dp, 0.dp)

                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = verdePrimario,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tela Inicial") },
                            onClick = { menuExpanded = false; navController.navigate("home") }
                        )
                        DropdownMenuItem(
                            text = { Text("Mapa") },
                            onClick = { menuExpanded = false; navController.navigate("mapa") }
                        )
                        DropdownMenuItem(
                            text = { Text("Perfil") },
                            onClick = { menuExpanded = false; navController.navigate("perfil") }
                        )
                        if (isVoluntario) {
                            DropdownMenuItem(
                                text = { Text("Inscrição voluntariado") },
                                onClick = { menuExpanded = false; navController.navigate("inscricao") }
                            )
                        } else {
                            DropdownMenuItem(
                                text = { Text("Fazer doação") },
                                onClick = { menuExpanded = false; navController.navigate("doacao") }
                            )
                        }
                        DropdownMenuItem(
                            text = { Text("Sair") },
                            onClick = {
                                menuExpanded = false
                                authViewModel.logout()
                                navController.navigate("login") { popUpTo(0) { inclusive = true } }
                            }
                        )
                    }
                }
            }

            // Seção: Notícias / Tarefas
            val secaoTitulo = if (isVoluntario) "Tarefas" else "Notícias"
            Text(
                text = secaoTitulo,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = verdePrimario,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(2) { index ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .width(180.dp)
                            .height(120.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(R.drawable.orfanato3),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .background(verdeSecundario.copy(alpha = 0.80f))
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (isVoluntario) {
                                        if (index == 0) "Dar aula de artes" else "Ajudar na educação"
                                    } else "Ver notícia",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.nunito_semibold))
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Seção: Orfanatos
            val orfanatosTitulo = if (isVoluntario) "Orfanatos atrelados a mim" else "Orfanatos próximos"
            Text(
                text = orfanatosTitulo,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = verdePrimario,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            if (orphanages.isEmpty()) {
                Text(
                    text = "Nenhum orfanato cadastrado ainda.",
                    color = colorResource(R.color.cor_text_login),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            } else {
                orphanages.forEach { o ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable {
                                orphanageViewModel.selectOrphanageForDetail(o)
                                navController.navigate("ong_detail")
                            }
                    ) {
                        Column {
                            Text(
                                text = o.nome,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                                fontSize = 15.sp,
                                color = verdePrimario
                            )
                            Box {
                                Image(
                                    painter = painterResource(R.drawable.orfanato3),
                                    contentDescription = o.nome,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(190.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .background(verdeSecundario.copy(alpha = 0.85f))
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Saiba mais",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

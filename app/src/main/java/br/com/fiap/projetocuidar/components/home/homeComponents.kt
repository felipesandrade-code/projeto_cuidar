package br.com.fiap.projetocuidar.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import coil.compose.AsyncImage

@Composable
fun HomeComponents(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel
) {
    val professionalPhotos = listOf(
        "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?q=80&w=800&auto=format&fit=crop", // 0: Crianças sinal de paz
        "https://images.unsplash.com/photo-1547038570-cb419a4e030a?q=80&w=800&auto=format&fit=crop", // 1: Menino e menina comendo
        "https://images.unsplash.com/photo-1531206715517-5c0ba140b2b8?q=80&w=800&auto=format&fit=crop", // 2: Grupo de voluntárias
        "https://images.pexels.com/photos/35250445/pexels-photo-35250445.jpeg?auto=compress&cs=tinysrgb&w=800", // 3: Crianças copos coloridos
        "https://images.pexels.com/photos/7100693/pexels-photo-7100693.jpeg?auto=compress&cs=tinysrgb&w=800", // 4: Crianças sob coqueiro
        "https://images.unsplash.com/photo-1524062734623-a26062363660?q=80&w=800&auto=format&fit=crop", // 5: Quatro crianças terra
        "https://images.unsplash.com/photo-1459183885447-df88d1f7124f?q=80&w=800&auto=format&fit=crop" // 6: Grupo mochilas laranjas
    )

    var menuExpanded by remember { mutableStateOf(false) }
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    val tipoUsuario = currentUser?.tipoUsuario?.lowercase() ?: ""
    val isVoluntario = tipoUsuario.contains("voluntário") || tipoUsuario.contains("voluntario")
    val isOrfanato = tipoUsuario.contains("orfanato") || tipoUsuario.contains("ong")

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
                } else if (isOrfanato) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { /* Navegar para mensagens recebidas */ },
                        icon = { Icon(Icons.Filled.Mail, contentDescription = "Mensagens", modifier = Modifier.size(24.dp)) },
                        label = { Text("Mensagens", fontSize = 11.sp, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
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
                        } else if (isOrfanato) {
                            DropdownMenuItem(
                                text = { Text("Editar dados da ONG") },
                                onClick = { menuExpanded = false; navController.navigate("registerOng") }
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

            // Seção: Notícias / Gestão
            val secaoTitulo = when {
                isVoluntario -> "Tarefas"
                isOrfanato -> "Minha Gestão"
                else -> "Notícias"
            }
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
                            AsyncImage(
                                model = professionalPhotos[index % professionalPhotos.size],
                                error = painterResource(R.drawable.orfanato3),
                                placeholder = painterResource(R.drawable.orfanato3),
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
                                    text = when {
                                        isVoluntario -> if (index == 0) "Dar aula de artes" else "Ajudar na educação"
                                        isOrfanato -> if (index == 0) "Estatísticas da ONG" else "Mensagens pendentes"
                                        else -> "Ver notícia"
                                    },
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

            // Dashboard do Orfanato (se for o caso)
            if (isOrfanato) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Doações (Mês)", fontSize = 12.sp, color = Color.Gray)
                                Text("R$ 1.250,00", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Novos Itens", fontSize = 12.sp, color = Color.Gray)
                                Text("24 unidades", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "Últimas Atividades",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        color = verdePrimario,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val atividades = listOf(
                        "João Silva enviou uma mensagem",
                        "Doação registrada: 5kg de Arroz",
                        "Nova oferta de voluntariado: Maria Souza",
                        "Doação registrada: R$ 50,00"
                    )

                    atividades.forEach { atividade ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.History,
                                    contentDescription = null,
                                    tint = verdePrimario,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    text = atividade,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.nunito_regular))
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Seção: Lista de Orfanatos / Meus Registros
            val orfanatosTitulo = if (isOrfanato) "Meus Registros" else if (isVoluntario) "Orfanatos atrelados a mim" else "Orfanatos próximos"
            Text(
                text = orfanatosTitulo,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = verdePrimario,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            if (orphanages.isEmpty()) {
                Text(
                    text = "Nenhum registro encontrado.",
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
                            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                                val seed = o.id.ifBlank { o.nome }.hashCode()
                                val photoUrl = o.fotoUrl ?: professionalPhotos[2 + (Math.abs(seed) % (professionalPhotos.size - 2))]
                                AsyncImage(
                                    model = photoUrl,
                                    error = painterResource(R.drawable.orfanato3),
                                    placeholder = painterResource(R.drawable.orfanato3),
                                    contentDescription = o.nome,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(190.dp),
                                    contentScale = ContentScale.Crop
                                )

                                // Botão de Excluir visível apenas para usuários Orfanato
                                if (isOrfanato) {
                                    IconButton(
                                        onClick = { orphanageViewModel.delete(o.id) },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(8.dp)
                                            .background(Color.White.copy(alpha = 0.8f), CircleShape)
                                            .size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Excluir",
                                            tint = Color.Red,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .background(verdeSecundario.copy(alpha = 0.85f))
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Ver detalhes",
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

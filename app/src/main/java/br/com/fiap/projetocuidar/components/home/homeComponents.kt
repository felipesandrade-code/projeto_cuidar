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
import androidx.compose.ui.draw.scale
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
import br.com.fiap.projetocuidar.components.navigation.AppBottomBar
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel
import coil.compose.AsyncImage

@Composable
fun HomeComponents(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel
) {
    val professionalPhotos = listOf(
        "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?q=80&w=800&auto=format&fit=crop", 
        "https://images.unsplash.com/photo-1547038570-cb419a4e030a?q=80&w=800&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1531206715517-5c0ba140b2b8?q=80&w=800&auto=format&fit=crop",
        "https://images.pexels.com/photos/35250445/pexels-photo-35250445.jpeg?auto=compress&cs=tinysrgb&w=800",
        "https://images.pexels.com/photos/7100693/pexels-photo-7100693.jpeg?auto=compress&cs=tinysrgb&w=800",
        "https://images.unsplash.com/photo-1524062734623-a26062363660?q=80&w=800&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1459183885447-df88d1f7124f?q=80&w=800&auto=format&fit=crop"
    )

    var menuExpanded by remember { mutableStateOf(false) }
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    val donations = managementViewModel.donations
    val volunteers = managementViewModel.volunteers
    val tipoUsuario = currentUser?.tipoUsuario?.lowercase() ?: ""
    val isVoluntario = tipoUsuario.contains("voluntário") || tipoUsuario.contains("voluntario")
    val isOrfanato = tipoUsuario.contains("orfanato") || tipoUsuario.contains("ong") || tipoUsuario.contains("operator") || tipoUsuario.contains("admin")

    val verdePrimario = colorResource(R.color.cor_registre)
    val verdeSecundario = colorResource(R.color.cor_card_footer)

    Scaffold(
        bottomBar = {
            AppBottomBar(
                navController = navController,
                authViewModel = authViewModel,
                currentRoute = "home"
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().background(colorResource(R.color.cor_column_registre)).padding(innerPadding).verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                LogoComponent("Logo", 100.dp, 0.dp, 0.dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isVoluntario) {
                        val myVolunteer = volunteers.find { it.email.lowercase() == currentUser?.email?.lowercase() }
                        val isAvailable = myVolunteer?.isAvailable ?: true
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.White, RoundedCornerShape(20.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                            Text(if (isAvailable) "Disponível" else "Ocupado", fontSize = 10.sp, color = if (isAvailable) verdePrimario else Color.Gray, modifier = Modifier.padding(end = 4.dp))
                            Switch(checked = isAvailable, onCheckedChange = { managementViewModel.updateVolunteerAvailability(currentUser?.email ?: "", it) }, modifier = Modifier.scale(0.6f), colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = verdePrimario, uncheckedThumbColor = Color.White, uncheckedTrackColor = Color.LightGray))
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                    Box {
                        IconButton(onClick = { menuExpanded = true }) { Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = verdePrimario, modifier = Modifier.size(32.dp)) }
                        DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                            DropdownMenuItem(text = { Text("Tela Inicial") }, onClick = { menuExpanded = false; navController.navigate("home") })
                            DropdownMenuItem(text = { Text("Mapa") }, onClick = { menuExpanded = false; navController.navigate("mapa") })
                            DropdownMenuItem(text = { Text("Perfil") }, onClick = { menuExpanded = false; navController.navigate("perfil") })
                            if (isVoluntario) {
                                DropdownMenuItem(text = { Text("Inscrição voluntariado") }, onClick = { menuExpanded = false; navController.navigate("inscricao") })
                            } else if (isOrfanato) {
                                DropdownMenuItem(text = { Text("Editar dados da ONG") }, onClick = { menuExpanded = false; navController.navigate("registerOng") })
                                DropdownMenuItem(text = { Text("Mensagens e Campanhas") }, onClick = { menuExpanded = false; navController.navigate("operator_messages") })
                            } else {
                                DropdownMenuItem(text = { Text("Fazer doação") }, onClick = { menuExpanded = false; navController.navigate("doacao") })
                            }
                            DropdownMenuItem(text = { Text("Sair") }, onClick = { menuExpanded = false; authViewModel.logout(); navController.navigate("login") { popUpTo(0) { inclusive = true } } })
                        }
                    }
                }
            }

            val secaoTitulo = when {
                isVoluntario -> "Vagas Disponíveis"
                isOrfanato -> "Minha Gestão"
                else -> "Notícias"
            }
            Text(text = secaoTitulo, fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)), color = verdePrimario, modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp))

            if (isVoluntario) {
                val myVolunteerRecords = volunteers.filter { it.email.lowercase() == currentUser?.email?.lowercase() }
                val myOrphanageIds = myVolunteerRecords.map { it.orphanageId }
                val myJoinedTaskIds = myVolunteerRecords.mapNotNull { it.taskId }
                val availableTasks = managementViewModel.tasks.filter { it.orphanageId in myOrphanageIds && it.id !in myJoinedTaskIds }
                var selectedTaskForDetail by remember { mutableStateOf<br.com.fiap.projetocuidar.data.Task?>(null) }

                if (availableTasks.isEmpty()) {
                    Text("Nenhuma vaga aberta nos seus orfanatos.", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                } else {
                    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(availableTasks) { task ->
                            Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.width(220.dp).height(140.dp).clickable { selectedTaskForDetail = task }) {
                                Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(12.dp)) {
                                    Text(task.title, fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario, maxLines = 1)
                                    Spacer(Modifier.height(4.dp))
                                    Text(task.description, fontSize = 11.sp, color = Color.Gray, maxLines = 3, modifier = Modifier.weight(1f))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(task.area, fontSize = 10.sp, color = verdePrimario)
                                        Text(task.date, fontSize = 10.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (selectedTaskForDetail != null) {
                    val task = selectedTaskForDetail!!
                    AlertDialog(
                        onDismissRequest = { selectedTaskForDetail = null },
                        title = { Text(task.title, fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                        text = {
                            Column {
                                Text("Área: ${task.area}", fontSize = 12.sp, color = verdePrimario, fontFamily = FontFamily(Font(R.font.nunito_bold)))
                                Spacer(Modifier.height(8.dp))
                                Text(task.description, fontSize = 14.sp)
                                Spacer(Modifier.height(16.dp))
                                Text("Data prevista: ${task.date}", fontSize = 12.sp, color = Color.Gray)
                            }
                        },
                        confirmButton = {
                            Button(colors = ButtonDefaults.buttonColors(containerColor = verdePrimario), onClick = { 
                                currentUser?.email?.let { email -> managementViewModel.applyForTask(email, task.id) }
                                selectedTaskForDetail = null 
                            }) { Text("Quero ajudar!", color = Color.White) }
                        },
                        dismissButton = { TextButton(onClick = { selectedTaskForDetail = null }) { Text("Voltar") } }
                    )
                }
            } else if (isOrfanato) {
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(2) { index ->
                        Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.width(180.dp).height(120.dp).clickable { if (index == 0) navController.navigate("operator_statistics") else navController.navigate("operator_messages") }) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                val cardImage = if (index == 0) "https://images.unsplash.com/photo-1460925895917-afdab827c52f?q=80&w=800&auto=format&fit=crop" else "https://images.unsplash.com/photo-1577563906417-45a11b3f9f7c?q=80&w=800&auto=format&fit=crop"
                                AsyncImage(model = cardImage, error = painterResource(R.drawable.orfanato3), placeholder = painterResource(R.drawable.orfanato3), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(verdeSecundario.copy(alpha = 0.85f)).padding(horizontal = 8.dp, vertical = 6.dp)) {
                                    Text(text = if (index == 0) "Estatísticas da ONG" else "Mensagens pendentes", color = Color.White, fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.nunito_semibold)))
                                }
                            }
                        }
                    }
                }
            } else {
                val allNews = managementViewModel.news
                if (allNews.isEmpty()) {
                    Text("Nenhuma notícia recente.", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                } else {
                    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(allNews) { news ->
                            Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.width(220.dp).height(140.dp)) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(model = news.imageUrl ?: professionalPhotos[3], error = painterResource(R.drawable.orfanato3), placeholder = painterResource(R.drawable.orfanato3), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                    Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(verdeSecundario.copy(alpha = 0.85f)).padding(8.dp)) {
                                        Column {
                                            Text(news.title, color = Color.White, fontSize = 13.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), maxLines = 1)
                                            Text(news.orphanageName, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isOrfanato) {
                val myOrphanage = orphanages.find { it.createdBy == currentUser?.id }
                val orphanageId = myOrphanage?.id ?: ""
                val myDonations = managementViewModel.getDonationsForOrphanage(orphanageId)
                val myVolunteers = managementViewModel.getVolunteersForOrphanage(orphanageId)
                val availableVolunteersCount = myVolunteers.count { it.isAvailable }
                val totalMoney = myDonations.filter { it.type == "Dinheiro" }.sumOf { it.value?.replace("R$", "")?.replace(",", ".")?.trim()?.toDoubleOrNull() ?: 0.0 }

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Card(modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Doações (Mês)", fontSize = 12.sp, color = Color.Gray)
                                Text("R$ ${String.format("%.2f", totalMoney)}", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                        Card(modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Voluntários", fontSize = 12.sp, color = Color.Gray)
                                Text("$availableVolunteersCount disponíveis", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Card(modifier = Modifier.weight(1f).height(100.dp).clickable { navController.navigate("operator_messages") }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Comunicação", fontSize = 12.sp, color = Color.Gray)
                                Text("Mensagens e Campanhas", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                        Card(modifier = Modifier.weight(1f).height(100.dp).clickable { navController.navigate("operator_customers") }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("CRM", fontSize = 12.sp, color = Color.Gray)
                                Text("Doadores", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Card(modifier = Modifier.weight(1f).height(100.dp).clickable { navController.navigate("operator_segments") }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Filtros", fontSize = 12.sp, color = Color.Gray)
                                Text("Segmentos", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                        Card(modifier = Modifier.weight(1f).height(100.dp).clickable { navController.navigate("operator_manage_news") }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Divulgação", fontSize = 12.sp, color = Color.Gray)
                                Text("Postar Notícia", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Card(modifier = Modifier.weight(1f).height(100.dp).clickable { navController.navigate("operator_manage_tasks") }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Voluntariado", fontSize = 12.sp, color = Color.Gray)
                                Text("Gerenciar Vagas", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                        Card(modifier = Modifier.weight(1f).height(100.dp).clickable { navController.navigate("operator_manage_volunteers") }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Equipe", fontSize = 12.sp, color = Color.Gray)
                                Text("Voluntários", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    Text("Últimas Atividades", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = verdePrimario, modifier = Modifier.padding(bottom = 8.dp))
                    if (myDonations.isEmpty() && myVolunteers.isEmpty()) {
                        Text("Nenhuma atividade recente.", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
                    }
                    myDonations.take(3).forEach { donation -> ActivityCard(text = "${donation.donorName} doou ${donation.type}${if (!donation.value.isNullOrBlank()) ": ${donation.value}" else ""}", icon = Icons.Filled.Favorite, color = verdePrimario) }
                    myVolunteers.take(2).forEach { volunteer -> ActivityCard(text = "Novo voluntário: ${volunteer.name} (${volunteer.task})", icon = Icons.Filled.Person, color = verdePrimario) }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            if (!isOrfanato) {
                val orfanatosTitulo = if (isVoluntario) "Orfanatos atrelados a mim" else "Orfanatos próximos"
                Text(text = orfanatosTitulo, fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)), color = verdePrimario, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
                val displayedOrphanages = if (isVoluntario) {
                    val myOrphanageIds = volunteers.filter { it.email.lowercase() == currentUser?.email?.lowercase() }.map { it.orphanageId }
                    orphanages.filter { it.id in myOrphanageIds }
                } else {
                    orphanages
                }

                if (displayedOrphanages.isEmpty()) {
                    Text(text = if (isVoluntario) "Você ainda não se inscreveu em nenhum orfanato." else "Nenhum registro encontrado.", color = colorResource(R.color.cor_text_login), fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.nunito_regular)), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                } else {
                    displayedOrphanages.forEach { o ->
                        Card(colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp).clickable { orphanageViewModel.selectOrphanageForDetail(o); navController.navigate("ong_detail") }) {
                            Column {
                                Text(text = o.nome, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontFamily = FontFamily(Font(R.font.nunito_semibold)), fontSize = 15.sp, color = verdePrimario)
                                Box {
                                    val seed = o.id.ifBlank { o.nome }.hashCode()
                                    val photoUrl = o.fotoUrl ?: professionalPhotos[2 + (Math.abs(seed) % (professionalPhotos.size - 2))]
                                    AsyncImage(model = photoUrl, error = painterResource(R.drawable.orfanato3), placeholder = painterResource(R.drawable.orfanato3), contentDescription = o.nome, modifier = Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Crop)
                                    Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(verdeSecundario.copy(alpha = 0.85f)).padding(vertical = 8.dp)) {
                                        Text(text = "Ver detalhes", color = Color.White, fontSize = 13.sp, fontFamily = FontFamily(Font(R.font.nunito_semibold)), modifier = Modifier.align(Alignment.Center))
                                    }
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

@Composable
fun ActivityCard(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(12.dp))
            Text(text = text, fontSize = 13.sp, fontFamily = FontFamily(Font(R.font.nunito_regular)))
        }
    }
}

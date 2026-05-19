package br.com.fiap.projetocuidar.Screens.operator

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel
import br.com.fiap.projetocuidar.data.News
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNewsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val myOrphanage = orphanageViewModel.orphanages.find { it.createdBy == currentUser?.id }
    val news = managementViewModel.getNewsForOrphanage(myOrphanage?.id ?: "")

    var showCreateDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { fotoUrl = it.toString() } }

    val verdePrimario = colorResource(R.color.cor_registre)

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White).statusBarsPadding()) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = verdePrimario, modifier = Modifier.size(22.dp))
                    }
                    Text("Gerenciar Notícias", fontFamily = FontFamily(Font(R.font.nunito_regular)), color = colorResource(R.color.cor_text_login), fontSize = 17.sp)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }, containerColor = verdePrimario, contentColor = Color.White) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(colorResource(R.color.cor_column_registre)).padding(padding)) {
            if (news.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma notícia publicada.", color = Color.Gray)
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(news) { item ->
                        NewsItem(item, onDelete = { managementViewModel.deleteNews(item.id) })
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Nova Notícia") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Conteúdo") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                    
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(8.dp)).background(Color.White).clickable { galleryLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (fotoUrl.isNotBlank()) {
                            AsyncImage(model = fotoUrl, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        } else {
                            Text("Toque para escolher foto", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (title.isNotBlank() && myOrphanage != null) {
                        managementViewModel.addNews(News(orphanageId = myOrphanage.id, orphanageName = myOrphanage.nome, title = title, content = content, imageUrl = fotoUrl))
                        showCreateDialog = false; title = ""; content = ""; fotoUrl = ""
                    }
                }) { Text("Publicar") }
            },
            dismissButton = { TextButton(onClick = { showCreateDialog = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
fun NewsItem(news: News, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            if (!news.imageUrl.isNullOrBlank()) {
                AsyncImage(model = news.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(120.dp), contentScale = ContentScale.Crop)
            }
            Column(Modifier.padding(12.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(news.title, fontFamily = FontFamily(Font(R.font.nunito_bold)), fontSize = 16.sp)
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.size(18.dp))
                    }
                }
                Text(news.content, fontSize = 13.sp, color = Color.Gray, maxLines = 2)
                Text(news.date, fontSize = 10.sp, color = Color.LightGray, modifier = Modifier.align(Alignment.End))
            }
        }
    }
}

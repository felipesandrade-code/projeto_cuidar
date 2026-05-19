package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel
import br.com.fiap.projetocuidar.data.MessageViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.Volunteer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageVolunteersScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel,
    messageViewModel: MessageViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    
    val myOrphanage = remember(currentUser, orphanages) {
        orphanages.find { it.createdBy == currentUser?.id }
    }
    val orphanageId = myOrphanage?.id ?: ""
    
    val allVolunteers = managementViewModel.getVolunteersForOrphanage(orphanageId)
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedVolunteerForMessage by remember { mutableStateOf<Volunteer?>(null) }
    var messageText by remember { mutableStateOf("") }

    val filteredVolunteers = remember(searchQuery, allVolunteers) {
        if (searchQuery.isBlank()) allVolunteers
        else allVolunteers.filter { 
            it.name.contains(searchQuery, ignoreCase = true) || 
            it.email.contains(searchQuery, ignoreCase = true) ||
            it.task.contains(searchQuery, ignoreCase = true)
        }
    }

    val verdePrimario = colorResource(R.color.cor_registre)
    val snackHostState = remember { SnackbarHostState() }
    val sendState by messageViewModel.sendState.collectAsState()

    LaunchedEffect(sendState) {
        sendState?.let {
            snackHostState.showSnackbar(it)
            messageViewModel.clearSendState()
        }
    }

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
                    Text("Gerenciar Voluntários", fontFamily = FontFamily(Font(R.font.nunito_regular)), color = colorResource(R.color.cor_text_login), fontSize = 17.sp)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        },
        snackbarHost = { SnackbarHost(snackHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar por nome, e-mail ou tarefa") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = verdePrimario,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            if (filteredVolunteers.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            if (searchQuery.isEmpty()) "Nenhum voluntário inscrito ainda." 
                            else "Nenhum resultado para \"$searchQuery\"",
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredVolunteers) { volunteer ->
                        VolunteerListItem(
                            volunteer = volunteer,
                            onMessageClick = { 
                                val id = volunteer.userId ?: volunteer.email
                                navController.navigate("chat/$id/${volunteer.name}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VolunteerListItem(volunteer: Volunteer, onMessageClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(colorResource(R.color.cor_registre).copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = colorResource(R.color.cor_registre))
            }
            
            Spacer(Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    volunteer.name,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    fontSize = 16.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                    Spacer(Modifier.width(4.dp))
                    Text(volunteer.email, fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8FFA0), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("Área: ${volunteer.task}", fontSize = 10.sp, color = colorResource(R.color.cor_registre), fontFamily = FontFamily(Font(R.font.nunito_semibold)))
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onMessageClick, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Message, contentDescription = "Mensagem", tint = colorResource(R.color.cor_registre), modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    volunteer.dateJoined,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(4.dp))
                if (volunteer.isAvailable) {
                    Text("Ativo", fontSize = 11.sp, color = colorResource(R.color.cor_registre), fontFamily = FontFamily(Font(R.font.nunito_bold)))
                } else {
                    Text("Ocupado", fontSize = 11.sp, color = Color.Gray)
                }
            }
        }
    }
}

package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.*
import br.com.fiap.projetocuidar.data.network.CampaignResponse
import br.com.fiap.projetocuidar.data.network.MessageResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    navController: NavController,
    currentUserId: String,
    isOrfanato: Boolean = true,
    messageViewModel: MessageViewModel = viewModel(),
    campaignViewModel: CampaignViewModel = viewModel(),
    segmentViewModel: SegmentViewModel = viewModel(),
    orphanageViewModel: OrphanageViewModel = viewModel(),
    managementViewModel: ManagementViewModel = viewModel(),
    customerViewModel: CustomerViewModel = viewModel()
) {
    val messageState by messageViewModel.state.collectAsState()
    val campaignState by campaignViewModel.state.collectAsState()
    val segmentState by segmentViewModel.state.collectAsState()
    val orphanageList = orphanageViewModel.orphanages
    val volunteerList = managementViewModel.volunteers
    
    // Obter lista de doadores (customers) para resolver nomes
    val customerState by customerViewModel.state.collectAsState()
    val donorList = if (customerState is CustomerUiState.Success) {
        (customerState as CustomerUiState.Success).customers
    } else emptyList()

    var selectedTab by remember { mutableStateOf(0) }
    var showCreateCampaignDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        messageViewModel.loadInbox(currentUserId)
        if (isOrfanato) {
            campaignViewModel.loadCampaigns()
            segmentViewModel.loadSegments()
            customerViewModel.loadCustomers() // Fundamental para mostrar nomes dos doadores
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Central de Comunicação", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    if (isOrfanato) {
                        IconButton(onClick = { showCreateCampaignDialog = true }) {
                            Icon(Icons.Default.Campaign, contentDescription = "Nova Campanha")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.cor_column_registre)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding)
        ) {
            val tabs = if (isOrfanato) listOf("Mensagens Diretas", "Campanhas Push") else listOf("Minhas Mensagens")
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    ) {
                        Text(title, modifier = Modifier.padding(16.dp))
                    }
                }
            }

            when {
                selectedTab == 0 -> InboxTab(messageState, navController, currentUserId, orphanageList, volunteerList, donorList)
                isOrfanato && selectedTab == 1 -> CampaignsTab(campaignState, campaignViewModel)
            }
        }
    }

    if (showCreateCampaignDialog) {
        CreateCampaignDialog(
            segmentState = segmentState,
            onDismiss = { showCreateCampaignDialog = false },
            onCreate = { title, body, segId ->
                campaignViewModel.createCampaign(title, body, segId)
                showCreateCampaignDialog = false
            }
        )
    }
}

@Composable
fun CreateCampaignDialog(
    segmentState: SegmentUiState,
    onDismiss: () -> Unit,
    onCreate: (String, String, String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var selectedSegmentId by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Campanha Push") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("Mensagem") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                Text("Enviar para:", fontSize = 13.sp, color = Color.Gray)
                if (segmentState is SegmentUiState.Success) {
                    segmentState.segments.forEach { seg ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = selectedSegmentId == seg.id, onClick = { selectedSegmentId = seg.id })
                            Text(seg.nome, fontSize = 13.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { if (title.isNotBlank()) onCreate(title, body, selectedSegmentId.ifBlank { null }) }) { Text("Criar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun InboxTab(
    state: MessageUiState, 
    navController: NavController, 
    currentUserId: String,
    orphanages: List<Orphanage>,
    volunteers: List<Volunteer>,
    donors: List<br.com.fiap.projetocuidar.data.network.CustomerResponse>
) {
    fun getNameForId(id: String?): String {
        if (id == null) return "Desconhecido"
        
        // 1. Procurar em Orfanatos
        val orphanage = orphanages.find { it.id == id || it.createdBy == id }
        if (orphanage != null) return orphanage.nome
        
        // 2. Procurar em Voluntários
        val volunteer = volunteers.find { it.userId == id || it.email == id }
        if (volunteer != null) return volunteer.name
        
        // 3. Procurar em Doadores (CRM)
        val donor = donors.find { it.id == id || it.email == id }
        if (donor != null) return "${donor.nome} ${donor.sobrenome}".trim()
        
        return "Usuário: ${id.take(4)}..."
    }

    when (state) {
        is MessageUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MessageUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.message, color = Color.Red)
            }
        }
        is MessageUiState.Success -> {
            if (state.messages.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma mensagem encontrada.", color = Color.Gray)
                }
            } else {
                val conversations = remember(state.messages, currentUserId) {
                    state.messages.groupBy { 
                        if (it.remetente == currentUserId) it.destinatario else it.remetente
                    }.mapValues { it.value.first() }
                }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(conversations.keys.toList()) { otherPartyId ->
                        val lastMsg = conversations[otherPartyId]!!
                        val isSentByMe = lastMsg.remetente == currentUserId
                        val otherPartyName = getNameForId(otherPartyId)
                        
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("chat/$otherPartyId/$otherPartyName")
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(colorResource(R.color.cor_registre).copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = colorResource(R.color.cor_registre))
                                }
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = if (isSentByMe) "Para: $otherPartyName" else "De: $otherPartyName",
                                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                                        fontSize = 15.sp,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = (if (isSentByMe) "Você: " else "") + lastMsg.conteudo,
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        maxLines = 1
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(lastMsg.createdAt?.take(10) ?: "", fontSize = 10.sp, color = Color.Gray)
                                    StatusChip(lastMsg.status)
                                }
                            }
                        }
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun CampaignsTab(state: CampaignUiState, viewModel: CampaignViewModel) {
    when (state) {
        is CampaignUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CampaignUiState.Success -> {
            if (state.campaigns.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma campanha disparada.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.campaigns) { campaign ->
                        CampaignCard(campaign, onSend = { viewModel.sendCampaign(campaign.id) })
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun CampaignCard(campaign: CampaignResponse, onSend: () -> Unit) {
    val statusColor = when (campaign.status) {
        "DRAFT" -> Color(0xFFFF9800)
        "SENT" -> Color(0xFF4CAF50)
        else -> Color.Gray
    }
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(campaign.title, fontFamily = FontFamily(Font(R.font.nunito_bold)), fontSize = 15.sp)
            Text(campaign.body, fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatusChip(campaign.status)
                if (campaign.status == "DRAFT") {
                    TextButton(onClick = onSend) { Text("DISPARAR AGORA") }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val color = when (status) {
        "ENVIADO", "SENT" -> Color(0xFF4CAF50)
        "DRAFT" -> Color(0xFFFF9800)
        else -> Color.Gray
    }
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(status, fontSize = 10.sp, color = color)
    }
}

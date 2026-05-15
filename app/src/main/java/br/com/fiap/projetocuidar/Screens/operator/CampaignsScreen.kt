package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Send
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
import br.com.fiap.projetocuidar.data.CampaignUiState
import br.com.fiap.projetocuidar.data.CampaignViewModel
import br.com.fiap.projetocuidar.data.SegmentUiState
import br.com.fiap.projetocuidar.data.SegmentViewModel
import br.com.fiap.projetocuidar.data.network.CampaignResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(
    navController: NavController,
    campaignViewModel: CampaignViewModel = viewModel(),
    segmentViewModel: SegmentViewModel = viewModel()
) {
    val state by campaignViewModel.state.collectAsState()
    val actionMessage by campaignViewModel.actionMessage.collectAsState()
    val segmentState by segmentViewModel.state.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var titleText by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }
    var deeplink by remember { mutableStateOf("") }
    var selectedSegmentId by remember { mutableStateOf("") }
    var statusFilter by remember { mutableStateOf<String?>(null) }
    var snackMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        campaignViewModel.loadCampaigns()
        segmentViewModel.loadSegments()
    }

    LaunchedEffect(actionMessage) {
        actionMessage?.let {
            snackMessage = it
            campaignViewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campanhas Push", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Criar campanha")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.cor_column_registre)
                )
            )
        },
        snackbarHost = {
            snackMessage?.let { msg ->
                Snackbar(
                    action = { TextButton(onClick = { snackMessage = null }) { Text("OK") } }
                ) { Text(msg) }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding)
        ) {
            // Status filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(null to "Todas", "DRAFT" to "Rascunho", "SENT" to "Enviadas", "SCHEDULED" to "Agendadas")
                    .forEach { (value, label) ->
                        FilterChip(
                            selected = statusFilter == value,
                            onClick = {
                                statusFilter = value
                                campaignViewModel.loadCampaigns(value)
                            },
                            label = { Text(label, fontSize = 12.sp) }
                        )
                    }
            }

            when (val s = state) {
                is CampaignUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is CampaignUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(s.message, color = Color.Red)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { campaignViewModel.loadCampaigns() }) { Text("Tentar novamente") }
                        }
                    }
                }
                is CampaignUiState.Success -> {
                    if (s.campaigns.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Campaign, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                                Spacer(Modifier.height(8.dp))
                                Text("Nenhuma campanha encontrada.", color = Color.Gray)
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { showCreateDialog = true }) { Text("Criar Campanha") }
                            }
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(s.campaigns) { campaign ->
                                CampaignCard(
                                    campaign = campaign,
                                    onSend = { campaignViewModel.sendCampaign(campaign.id) }
                                )
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Nova Campanha") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = titleText,
                        onValueChange = { titleText = it },
                        label = { Text("Título *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = bodyText,
                        onValueChange = { bodyText = it },
                        label = { Text("Corpo *") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    OutlinedTextField(
                        value = deeplink,
                        onValueChange = { deeplink = it },
                        label = { Text("Deeplink (ex: cuidar://orfanato/id)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Text("Segmento (opcional):", fontSize = 13.sp)
                    if (segmentState is SegmentUiState.Success) {
                        (segmentState as SegmentUiState.Success).segments.forEach { seg ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedSegmentId == seg.id,
                                    onClick = { selectedSegmentId = if (selectedSegmentId == seg.id) "" else seg.id }
                                )
                                Text(seg.nome, fontSize = 13.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (titleText.isNotBlank() && bodyText.isNotBlank()) {
                        campaignViewModel.createCampaign(
                            titleText,
                            bodyText,
                            selectedSegmentId.ifBlank { null },
                            deeplink = deeplink.ifBlank { null }
                        )
                        titleText = ""; bodyText = ""; deeplink = ""; selectedSegmentId = ""
                        showCreateDialog = false
                    }
                }) { Text("Criar") }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun CampaignCard(campaign: CampaignResponse, onSend: () -> Unit) {
    val statusColor = when (campaign.status) {
        "DRAFT" -> Color(0xFFFF9800)
        "SENT" -> Color(0xFF4CAF50)
        "SCHEDULED" -> Color(0xFF2196F3)
        else -> Color.Gray
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(campaign.title, fontFamily = FontFamily(Font(R.font.nunito_semibold)), fontSize = 15.sp, modifier = Modifier.weight(1f))
                Box(
                    Modifier
                        .background(statusColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(campaign.status, fontSize = 10.sp, color = statusColor)
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(campaign.body, fontSize = 13.sp, color = Color.Gray, maxLines = 2)
            campaign.deeplink?.let {
                Text("Deeplink: $it", fontSize = 11.sp, color = Color(0xFF2196F3))
            }
            if (campaign.status == "DRAFT") {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onSend,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF51701A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Enviar Agora via FCM", fontSize = 12.sp)
                }
            }
        }
    }
}

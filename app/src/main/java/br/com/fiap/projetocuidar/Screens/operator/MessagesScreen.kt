package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.fiap.projetocuidar.data.MessageUiState
import br.com.fiap.projetocuidar.data.MessageViewModel
import br.com.fiap.projetocuidar.data.SegmentViewModel
import br.com.fiap.projetocuidar.data.SegmentUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    navController: NavController,
    currentUserId: String,
    messageViewModel: MessageViewModel = viewModel(),
    segmentViewModel: SegmentViewModel = viewModel()
) {
    val messageState by messageViewModel.state.collectAsState()
    val sendState by messageViewModel.sendState.collectAsState()
    val segmentState by segmentViewModel.state.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    var showSendDialog by remember { mutableStateOf(false) }
    var messageContent by remember { mutableStateOf("") }
    var destinatario by remember { mutableStateOf("") }
    var selectedSegmentId by remember { mutableStateOf<String?>(null) }
    var isBroadcast by remember { mutableStateOf(false) }
    var snackMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        messageViewModel.loadInbox(currentUserId)
        segmentViewModel.loadSegments()
    }

    LaunchedEffect(sendState) {
        sendState?.let {
            snackMessage = it
            messageViewModel.clearSendState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mensagens", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showSendDialog = true }) {
                        Icon(Icons.Default.Send, contentDescription = "Enviar mensagem")
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
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = {
                    selectedTab = 0
                    messageViewModel.loadInbox(currentUserId)
                }) { Text("Inbox", modifier = Modifier.padding(16.dp)) }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Enviar", modifier = Modifier.padding(16.dp))
                }
            }

            when (selectedTab) {
                0 -> InboxTab(messageState)
                1 -> SendTab(
                    segmentState = segmentState,
                    onSendIndividual = { dest, content ->
                        messageViewModel.sendMessage(destinatario = dest, conteudo = content)
                    },
                    onSendBroadcast = { segId, content ->
                        messageViewModel.sendMessage(segmentoId = segId, conteudo = content)
                    }
                )
            }
        }
    }
}

@Composable
private fun InboxTab(state: MessageUiState) {
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
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.messages) { msg ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(msg.conteudo, fontSize = 14.sp)
                                Spacer(Modifier.height(4.dp))
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    StatusChip(msg.status)
                                    Text(msg.createdAt?.take(10) ?: "", fontSize = 11.sp, color = Color.Gray)
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
private fun SendTab(
    segmentState: SegmentUiState,
    onSendIndividual: (String, String) -> Unit,
    onSendBroadcast: (String, String) -> Unit
) {
    var isBroadcast by remember { mutableStateOf(false) }
    var destinatario by remember { mutableStateOf("") }
    var selectedSegmentId by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("TEXT") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Tipo de Envio", fontFamily = FontFamily(Font(R.font.nunito_semibold)))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = !isBroadcast,
                onClick = { isBroadcast = false },
                label = { Text("Individual") }
            )
            FilterChip(
                selected = isBroadcast,
                onClick = { isBroadcast = true },
                label = { Text("Broadcast") }
            )
        }

        if (!isBroadcast) {
            OutlinedTextField(
                value = destinatario,
                onValueChange = { destinatario = it },
                label = { Text("ID do destinatário *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        } else {
            Text("Segmento de destino:", fontSize = 13.sp, color = Color.Gray)
            if (segmentState is SegmentUiState.Success) {
                segmentState.segments.forEach { seg ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedSegmentId == seg.id,
                            onClick = { selectedSegmentId = seg.id }
                        )
                        Text(seg.nome, fontSize = 13.sp)
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("TEXT", "CAMPANHA").forEach { t ->
                FilterChip(selected = tipo == t, onClick = { tipo = t }, label = { Text(t) })
            }
        }

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Conteúdo *") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Button(
            onClick = {
                if (content.isNotBlank()) {
                    if (!isBroadcast && destinatario.isNotBlank()) {
                        onSendIndividual(destinatario, content)
                    } else if (isBroadcast && selectedSegmentId.isNotBlank()) {
                        onSendBroadcast(selectedSegmentId, content)
                    }
                    content = ""; destinatario = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF51701A))
        ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Enviar Mensagem")
        }
    }
}

@Composable
private fun StatusChip(status: String) {
    val color = when (status) {
        "ENVIADO" -> Color(0xFF2196F3)
        "ENTREGUE" -> Color(0xFF4CAF50)
        "LIDO" -> Color(0xFF8BC34A)
        "FALHA" -> Color(0xFFF44336)
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

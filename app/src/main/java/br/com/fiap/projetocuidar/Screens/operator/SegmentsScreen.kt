package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Group
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
import br.com.fiap.projetocuidar.data.SegmentUiState
import br.com.fiap.projetocuidar.data.SegmentViewModel
import br.com.fiap.projetocuidar.data.network.SegmentResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentsScreen(
    navController: NavController,
    segmentViewModel: SegmentViewModel = viewModel()
) {
    val state by segmentViewModel.state.collectAsState()
    val actionMessage by segmentViewModel.actionMessage.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var newSegmentName by remember { mutableStateOf("") }
    var newSegmentDesc by remember { mutableStateOf("") }
    var newSegmentTags by remember { mutableStateOf("") }
    var snackMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { segmentViewModel.loadSegments() }

    LaunchedEffect(actionMessage) {
        actionMessage?.let {
            snackMessage = it
            segmentViewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Segmentos", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Criar segmento")
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
            when (val s = state) {
                is SegmentUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is SegmentUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(s.message, color = Color.Red)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { segmentViewModel.loadSegments() }) { Text("Tentar novamente") }
                        }
                    }
                }
                is SegmentUiState.Success -> {
                    if (s.segments.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Group, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                                Spacer(Modifier.height(8.dp))
                                Text("Nenhum segmento criado ainda.", color = Color.Gray)
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { showCreateDialog = true }) { Text("Criar Segmento") }
                            }
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(s.segments) { segment ->
                                SegmentCard(
                                    segment = segment,
                                    onDelete = { segmentViewModel.deleteSegment(segment.id) }
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
            title = { Text("Novo Segmento") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newSegmentName,
                        onValueChange = { newSegmentName = it },
                        label = { Text("Nome *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newSegmentDesc,
                        onValueChange = { newSegmentDesc = it },
                        label = { Text("Descrição") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newSegmentTags,
                        onValueChange = { newSegmentTags = it },
                        label = { Text("Tags (separadas por vírgula)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newSegmentName.isNotBlank()) {
                        val tags = newSegmentTags.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        segmentViewModel.createSegment(newSegmentName, newSegmentDesc.ifBlank { null }, tags)
                        newSegmentName = ""; newSegmentDesc = ""; newSegmentTags = ""
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
private fun SegmentCard(segment: SegmentResponse, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Group, contentDescription = null, tint = Color(0xFF51701A), modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(segment.nome, fontFamily = FontFamily(Font(R.font.nunito_semibold)), fontSize = 15.sp)
                segment.descricao?.let { Text(it, fontSize = 12.sp, color = Color.Gray) }
                if (segment.tags.isNotEmpty()) {
                    Text(
                        segment.tags.joinToString(", "),
                        fontSize = 11.sp,
                        color = Color(0xFF51701A)
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = Color(0xFFF44336))
            }
        }
    }
}

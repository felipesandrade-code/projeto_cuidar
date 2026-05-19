package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
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
import br.com.fiap.projetocuidar.data.AuditUiState
import br.com.fiap.projetocuidar.data.AuditViewModel
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.network.AuditLogResponse

private val ACTIONS = listOf(
    "CREATE_CAMPAIGN", "SEND_CAMPAIGN", "UPDATE_CUSTOMER", "ADD_NOTE", "CREATE_ORPHANAGE", "DELETE_ORPHANAGE"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditLogsScreen(
    navController: NavController,
    auditViewModel: AuditViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val state by auditViewModel.state.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    var selectedAction by remember { mutableStateOf<String?>(null) }
    var showFilterDialog by remember { mutableStateOf(false) }

    // Segurança: Sempre filtrar logs pelo ID do usuário logado no app
    LaunchedEffect(currentUser) {
        auditViewModel.loadLogs(userId = currentUser?.id)
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = colorResource(R.color.cor_registre), modifier = Modifier.size(22.dp))
                    }
                    Text("Logs de Auditoria", fontFamily = FontFamily(Font(R.font.nunito_regular)), color = colorResource(R.color.cor_text_login), fontSize = 17.sp)
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrar", tint = colorResource(R.color.cor_registre), modifier = Modifier.size(22.dp))
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding)
        ) {
            selectedAction?.let { action ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Filtro: ", fontSize = 13.sp, color = Color.Gray)
                    FilterChip(
                        selected = true,
                        onClick = {
                            selectedAction = null
                            auditViewModel.loadLogs(userId = currentUser?.id)
                        },
                        label = { Text(action) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(R.color.cor_registre).copy(alpha = 0.15f),
                            selectedLabelColor = colorResource(R.color.cor_registre)
                        )
                    )
                }
            }

            when (val s = state) {
                is AuditUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is AuditUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(s.message, color = Color.Red)
                    }
                }
                is AuditUiState.Success -> {
                    if (s.logs.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum log encontrado para suas ações.", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(s.logs) { log ->
                                LogItem(log)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text("Filtrar por Ação") },
            text = {
                Column {
                    ACTIONS.forEach { action ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable {
                                selectedAction = action
                                auditViewModel.loadLogs(userId = currentUser?.id, acao = action)
                                showFilterDialog = false
                            }
                        ) {
                            RadioButton(selected = selectedAction == action, onClick = null)
                            Text(action, modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFilterDialog = false }) { Text("Fechar") }
            }
        )
    }
}

@Composable
fun LogItem(log: AuditLogResponse) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = log.acao.replace("_", " "),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 15.sp,
                    color = colorResource(R.color.cor_registre)
                )
                // Exibir Data e Hora Completa
                Text(
                    text = log.timestamp?.take(19)?.replace("T", " ") ?: "",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace
                )
            }
            
            HorizontalDivider(Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.3f))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    log.recurso?.let { DetailRow("Recurso", it) }
                    // Se for mensagem, tentar mostrar o destinatário nos detalhes ou ID Ref
                    if (log.acao.contains("MESSAGE") || log.acao.contains("SEND")) {
                        log.recursoId?.let { DetailRow("Destinatário (ID)", it.take(8) + "...") }
                    } else {
                        log.recursoId?.let { DetailRow("ID Ref", it.take(8) + "...") }
                    }
                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    log.ip?.let { DetailRow("Origem (IP)", it) }
                    log.role?.let { DetailRow("Permissão", it) }
                }
            }

            if (!log.details.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = log.details,
                        fontSize = 11.sp,
                        color = Color.DarkGray,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(label, fontSize = 9.sp, color = Color.Gray, style = MaterialTheme.typography.labelSmall)
        Text(value, fontSize = 12.sp, color = Color.Black, fontFamily = FontFamily(Font(R.font.nunito_regular)))
    }
}

package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import br.com.fiap.projetocuidar.data.network.AuditLogResponse

private val COMMON_ACTIONS = listOf(
    "Todas", "LOGIN", "REGISTER", "SEND_MESSAGE", "READ_MESSAGE",
    "CREATE_CAMPAIGN", "SEND_CAMPAIGN", "UPDATE_CUSTOMER", "ADD_NOTE", "CREATE_ORPHANAGE"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditLogsScreen(
    navController: NavController,
    auditViewModel: AuditViewModel = viewModel()
) {
    val state by auditViewModel.state.collectAsState()
    var selectedAction by remember { mutableStateOf<String?>(null) }
    var showFilterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { auditViewModel.loadLogs() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Logs de Auditoria", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
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
            selectedAction?.let { action ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Filtro: ", fontSize = 13.sp, color = Color.Gray)
                    FilterChip(
                        selected = true,
                        onClick = {
                            selectedAction = null
                            auditViewModel.loadLogs()
                        },
                        label = { Text(action) }
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
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(s.message, color = Color.Red)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { auditViewModel.loadLogs() }) { Text("Tentar novamente") }
                        }
                    }
                }
                is AuditUiState.Success -> {
                    if (s.logs.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum log encontrado.", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(s.logs) { log ->
                                AuditLogCard(log)
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
                    COMMON_ACTIONS.forEach { action ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = (if (action == "Todas") null else action) == selectedAction,
                                onClick = {
                                    selectedAction = if (action == "Todas") null else action
                                    auditViewModel.loadLogs(acao = selectedAction)
                                    showFilterDialog = false
                                }
                            )
                            Text(action, fontSize = 13.sp)
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
private fun AuditLogCard(log: AuditLogResponse) {
    val actionColor = when {
        log.acao.contains("LOGIN") || log.acao.contains("REGISTER") -> Color(0xFF2196F3)
        log.acao.contains("SEND") || log.acao.contains("CREATE") -> Color(0xFF4CAF50)
        log.acao.contains("UPDATE") || log.acao.contains("ADD") -> Color(0xFFFF9800)
        else -> Color(0xFF9E9E9E)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(actionColor.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Text(log.acao, fontSize = 10.sp, color = actionColor)
            }
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                log.details?.let { Text(it, fontSize = 12.sp, maxLines = 1) }
                log.userId?.let { Text("Usuário: $it", fontSize = 11.sp, color = Color.Gray) }
            }
            log.timestamp?.let {
                Text(it.take(10), fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

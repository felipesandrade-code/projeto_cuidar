package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.CustomerViewModel
import br.com.fiap.projetocuidar.data.MessageViewModel
import br.com.fiap.projetocuidar.data.TimelineState
import br.com.fiap.projetocuidar.data.network.CustomerResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    navController: NavController,
    customer: CustomerResponse,
    customerViewModel: CustomerViewModel = viewModel(),
    messageViewModel: MessageViewModel = viewModel()
) {
    val timelineState by customerViewModel.timelineState.collectAsState()
    val actionMessage by customerViewModel.actionMessage.collectAsState()
    val sendState by messageViewModel.sendState.collectAsState()

    var noteText by remember { mutableStateOf("") }
    var showNoteDialog by remember { mutableStateOf(false) }
    var showMessageDialog by remember { mutableStateOf("") }
    var showMessageDialogVisible by remember { mutableStateOf(false) }
    
    // Estados para edição de perfil (Tags e Score)
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var editScore by remember { mutableStateOf(customer.score.toString()) }
    var editTags by remember { mutableStateOf(customer.tags.joinToString(", ")) }

    LaunchedEffect(customer.id) { customerViewModel.loadTimeline(customer.id) }

    LaunchedEffect(actionMessage) {
        if (actionMessage != null) {
            // Recarregar dados após atualização
            customerViewModel.loadCustomers()
            customerViewModel.clearMessage()
        }
    }
    LaunchedEffect(sendState) {
        if (sendState != null) messageViewModel.clearSendState()
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
                    Text("${customer.nome} ${customer.sobrenome}", fontFamily = FontFamily(Font(R.font.nunito_regular)), color = colorResource(R.color.cor_text_login), fontSize = 17.sp, maxLines = 1)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Info card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Informações", fontFamily = FontFamily(Font(R.font.nunito_semibold)), fontSize = 16.sp)
                        Spacer(Modifier.height(8.dp))
                        InfoRow("E-mail", customer.email)
                        InfoRow("Telefone", customer.telefone ?: "—")
                        InfoRow("Status", customer.status)
                        InfoRow("Score", customer.score.toString())
                        InfoRow("Tags", customer.tags.joinToString(", ").ifEmpty { "—" })
                        InfoRow("Cadastro", customer.createdAt?.take(10) ?: "—")
                    }
                }
            }

            // Action buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showMessageDialogVisible = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF51701A)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Message, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Mensagem", fontSize = 11.sp)
                    }
                    Button(
                        onClick = { showEditProfileDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_registre)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Editar Perfil", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = { showNoteDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Note, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Nota", fontSize = 11.sp)
                    }
                }
            }

            when (val s = timelineState) {
                is TimelineState.Loading -> {
                    item {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is TimelineState.Success -> {
                    // Notes
                    if (s.timeline.notas.isNotEmpty()) {
                        item {
                            SectionTitle("Notas (${s.timeline.notas.size})")
                        }
                        items(s.timeline.notas) { nota ->
                            NoteItem(nota)
                        }
                    }

                    // Segments
                    if (s.timeline.segmentos.isNotEmpty()) {
                        item { SectionTitle("Segmentos") }
                        items(s.timeline.segmentos) { seg ->
                            ChipItem(seg.nome)
                        }
                    }

                    // Last messages
                    if (s.timeline.ultimasMensagens.isNotEmpty()) {
                        item { SectionTitle("Últimas Mensagens") }
                        items(s.timeline.ultimasMensagens.take(5)) { msg ->
                            MessageItem(msg.conteudo, msg.status, msg.createdAt)
                        }
                    }

                    // Last campaigns
                    if (s.timeline.ultimasCampanhasRecebidas.isNotEmpty()) {
                        item { SectionTitle("Campanhas Recebidas") }
                        items(s.timeline.ultimasCampanhasRecebidas.take(3)) { camp ->
                            MessageItem(camp.title, camp.status, null)
                        }
                    }
                }
                is TimelineState.Error -> {
                    item { Text(s.message, color = Color.Red) }
                }
                else -> {}
            }
        }
    }

    // Edit Profile Dialog (Score e Tags)
    if (showEditProfileDialog) {
        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Editar Perfil do Doador") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = editScore,
                        onValueChange = { if (it.all { c -> c.isDigit() }) editScore = it },
                        label = { Text("Score de Engajamento") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = editTags,
                        onValueChange = { editTags = it },
                        label = { Text("Tags (separadas por vírgula)") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ex: Fiel, Alimentos, Empresa") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val tagsList = editTags.split(",").map { it.trim() }.filter { it.isNotBlank() }
                    customerViewModel.updateCustomer(
                        id = customer.id,
                        score = editScore.toIntOrNull(),
                        tags = tagsList
                    )
                    showEditProfileDialog = false
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) { Text("Cancelar") }
            }
        )
    }

    // Note dialog
    if (showNoteDialog) {
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = { Text("Nova Nota") },
            text = {
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    placeholder = { Text("Escreva uma nota...") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (noteText.isNotBlank()) {
                        customerViewModel.addNote(customer.id, noteText)
                        noteText = ""
                        showNoteDialog = false
                    }
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) { Text("Cancelar") }
            }
        )
    }

    // Send message dialog
    if (showMessageDialogVisible) {
        AlertDialog(
            onDismissRequest = { showMessageDialogVisible = false },
            title = { Text("Enviar Mensagem") },
            text = {
                OutlinedTextField(
                    value = showMessageDialog,
                    onValueChange = { showMessageDialog = it },
                    placeholder = { Text("Digite a mensagem...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (showMessageDialog.isNotBlank()) {
                        messageViewModel.sendMessage(
                            senderId = "system", // Placeholder
                            destinatario = customer.id, 
                            conteudo = showMessageDialog
                        )
                        showMessageDialog = ""
                        showMessageDialogVisible = false
                    }
                }) { Text("Enviar") }
            },
            dismissButton = {
                TextButton(onClick = { showMessageDialogVisible = false }) { Text("Cancelar") }
            }
        )
    }

}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text("$label: ", fontSize = 13.sp, color = Color.Gray)
        Text(value, fontSize = 13.sp)
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        title,
        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
        fontSize = 15.sp,
        color = Color(0xFF51701A),
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
private fun NoteItem(nota: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBE7)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
            Icon(Icons.Default.Note, contentDescription = null, tint = Color(0xFF75874F), modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(nota, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ChipItem(label: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFE8FFA0), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(label, fontSize = 13.sp, color = Color(0xFF51701A))
    }
}

@Composable
private fun MessageItem(conteudo: String, status: String, createdAt: String?) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(conteudo, fontSize = 13.sp, maxLines = 2)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(status, fontSize = 11.sp, color = Color(0xFF51701A))
                Text(createdAt?.take(10) ?: "", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.MessageUiState
import br.com.fiap.projetocuidar.data.MessageViewModel
import br.com.fiap.projetocuidar.data.network.MessageResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    orphanageId: String,
    orphanageName: String,
    authViewModel: AuthViewModel,
    messageViewModel: MessageViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val messageState by messageViewModel.state.collectAsState()
    var textMessage by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val currentUserId = currentUser?.id

    LaunchedEffect(currentUserId) {
        currentUserId?.let { messageViewModel.loadInbox(it) }
    }

    val chatMessages = remember(messageState, currentUserId, orphanageId) {
        if (messageState is MessageUiState.Success) {
            val list = (messageState as MessageUiState.Success).messages
            // Filtrar mensagens desta conversa específica
            list.filter { 
                (it.remetente == currentUserId && it.destinatario == orphanageId) ||
                (it.remetente == orphanageId && it.destinatario == currentUserId)
            }.sortedBy { it.createdAt ?: "" }
        } else emptyList()
    }

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = colorResource(R.color.cor_registre), modifier = Modifier.size(22.dp))
                    }
                    Text(orphanageName, fontFamily = FontFamily(Font(R.font.nunito_regular)), color = colorResource(R.color.cor_text_login), fontSize = 17.sp, maxLines = 1)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorResource(R.color.cor_column_registre))
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (chatMessages.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (messageState is MessageUiState.Loading) {
                            CircularProgressIndicator(color = colorResource(R.color.cor_registre))
                        } else {
                            Text("Inicie uma conversa!", color = Color.Gray)
                        }
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(chatMessages) { msg ->
                            // A mensagem é minha se eu sou o remetente
                            val isMine = msg.remetente == currentUserId
                            
                            MessageBubble(msg, isMine)
                        }
                    }
                }
            }

            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .navigationBarsPadding()
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textMessage,
                        onValueChange = { textMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Digite sua mensagem...") },
                        maxLines = 4,
                        shape = RoundedCornerShape(28.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.cor_registre),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = {
                            if (textMessage.isNotBlank() && currentUserId != null) {
                                messageViewModel.sendMessage(
                                    senderId = currentUserId,
                                    destinatario = orphanageId,
                                    segmentoId = null,
                                    conteudo = textMessage
                                )
                                textMessage = ""
                            }
                        },
                        containerColor = colorResource(R.color.cor_registre),
                        contentColor = Color.White,
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Enviar")
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(msg: MessageResponse, isMine: Boolean) {
    val alignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (isMine) colorResource(R.color.cor_registre) else Color.White
    val textColor = if (isMine) Color.White else Color.Black
    val shape = if (isMine) {
        RoundedCornerShape(18.dp, 18.dp, 2.dp, 18.dp)
    } else {
        RoundedCornerShape(18.dp, 18.dp, 18.dp, 2.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = shape,
            color = bgColor,
            shadowElevation = 2.dp,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                Text(
                    text = msg.conteudo,
                    color = textColor,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = msg.createdAt?.take(16)?.replace("T", " ") ?: "",
                    color = textColor.copy(alpha = 0.6f),
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

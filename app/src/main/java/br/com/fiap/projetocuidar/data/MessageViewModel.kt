package br.com.fiap.projetocuidar.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MessageUiState {
    object Idle : MessageUiState()
    object Loading : MessageUiState()
    data class Success(val messages: List<MessageResponse>) : MessageUiState()
    data class Error(val message: String) : MessageUiState()
}

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val storage = MessageStorage(application, moshi)

    private val _state = MutableStateFlow<MessageUiState>(MessageUiState.Idle)
    val state: StateFlow<MessageUiState> = _state

    private val _sendState = MutableStateFlow<String?>(null)
    val sendState: StateFlow<String?> = _sendState

    private val _localMessages = MutableStateFlow<List<MessageResponse>>(emptyList())

    init {
        loadLocalMessages()
    }

    private fun loadLocalMessages() {
        viewModelScope.launch {
            _localMessages.value = storage.loadMessages()
        }
    }

    fun loadInbox(clienteId: String, page: Int = 0) {
        if (clienteId.isBlank()) return
        _state.value = MessageUiState.Loading
        viewModelScope.launch {
            try {
                val paged = ApiClient.api.getInbox(clienteId, page)
                val allMessages = (paged.content + _localMessages.value)
                    .filter { it.remetente == clienteId || it.destinatario == clienteId }
                    .distinctBy { it.id }
                    .sortedByDescending { it.createdAt ?: "" }
                
                _state.value = MessageUiState.Success(allMessages)
            } catch (e: Exception) {
                if (_localMessages.value.isNotEmpty()) {
                    val filtered = _localMessages.value
                        .filter { it.remetente == clienteId || it.destinatario == clienteId }
                        .sortedByDescending { it.createdAt ?: "" }
                    _state.value = MessageUiState.Success(filtered)
                } else {
                    _state.value = MessageUiState.Error("Erro ao carregar mensagens: ${e.message}")
                }
            }
        }
    }

    fun sendMessage(senderId: String, destinatario: String? = null, segmentoId: String? = null, tipo: String = "TEXT", conteudo: String) {
        viewModelScope.launch {
            try {
                android.util.Log.d("CHAT_DEBUG", "Enviando de: $senderId para: $destinatario")
                
                val responseList = ApiClient.api.sendMessage(
                    MessageRequest(
                        remetente = senderId.trim(),
                        destinatario = destinatario?.trim()?.ifBlank { null },
                        segmentoId = segmentoId?.trim()?.ifBlank { null },
                        tipo = tipo,
                        conteudo = conteudo
                    )
                )
                
                val lastSentMessage = responseList.firstOrNull()
                
                if (lastSentMessage != null) {
                    _sendState.value = "Mensagem enviada com sucesso."
                    val newList = listOf(lastSentMessage) + _localMessages.value
                    _localMessages.value = newList
                    storage.saveMessages(newList)
                    
                    val currentState = _state.value
                    if (currentState is MessageUiState.Success) {
                        val updatedList = (listOf(lastSentMessage) + currentState.messages).distinctBy { it.id }
                        _state.value = MessageUiState.Success(updatedList)
                    } else {
                        _state.value = MessageUiState.Success(listOf(lastSentMessage))
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("CHAT_DEBUG", "Erro ao enviar: ${e.message}")
                val errorMsg = e.message ?: "Erro desconhecido"
                _sendState.value = if (errorMsg.contains("403")) "Permissão Negada (Role)" else "Erro: $errorMsg"
            }
        }
    }

    fun markAsRead(messageId: String) {
        viewModelScope.launch {
            runCatching {
                ApiClient.api.updateMessageStatus(messageId, UpdateMessageStatusRequest("ENTREGUE"))
            }
        }
    }

    fun clearSendState() { _sendState.value = null }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageViewModel(app) as T
        }
    }
}

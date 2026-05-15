package br.com.fiap.projetocuidar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.ApiClient
import br.com.fiap.projetocuidar.data.network.MessageRequest
import br.com.fiap.projetocuidar.data.network.MessageResponse
import br.com.fiap.projetocuidar.data.network.UpdateMessageStatusRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MessageUiState {
    object Idle : MessageUiState()
    object Loading : MessageUiState()
    data class Success(val messages: List<MessageResponse>) : MessageUiState()
    data class Error(val message: String) : MessageUiState()
}

class MessageViewModel : ViewModel() {

    private val _state = MutableStateFlow<MessageUiState>(MessageUiState.Idle)
    val state: StateFlow<MessageUiState> = _state

    private val _sendState = MutableStateFlow<String?>(null)
    val sendState: StateFlow<String?> = _sendState

    fun loadInbox(clienteId: String, page: Int = 0) {
        _state.value = MessageUiState.Loading
        viewModelScope.launch {
            try {
                val paged = ApiClient.api.getInbox(clienteId, page)
                _state.value = MessageUiState.Success(paged.content)
            } catch (e: Exception) {
                _state.value = MessageUiState.Error("Erro ao carregar mensagens.")
            }
        }
    }

    fun sendMessage(destinatario: String? = null, segmentoId: String? = null, tipo: String = "TEXT", conteudo: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.sendMessage(MessageRequest(destinatario, segmentoId, tipo, conteudo))
                _sendState.value = "Mensagem enviada com sucesso."
            } catch (e: Exception) {
                _sendState.value = "Erro ao enviar mensagem."
            }
        }
    }

    fun markAsRead(messageId: String) {
        viewModelScope.launch {
            runCatching {
                ApiClient.api.updateMessageStatus(messageId, UpdateMessageStatusRequest("LIDO"))
            }
        }
    }

    fun markAsDelivered(messageId: String) {
        viewModelScope.launch {
            runCatching {
                ApiClient.api.updateMessageStatus(messageId, UpdateMessageStatusRequest("ENTREGUE"))
            }
        }
    }

    fun clearSendState() { _sendState.value = null }
}

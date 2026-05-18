package br.com.fiap.projetocuidar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.AddClientToSegmentRequest
import br.com.fiap.projetocuidar.data.network.ApiClient
import br.com.fiap.projetocuidar.data.network.CreateSegmentRequest
import br.com.fiap.projetocuidar.data.network.SegmentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SegmentUiState {
    object Idle : SegmentUiState()
    object Loading : SegmentUiState()
    data class Success(val segments: List<SegmentResponse>) : SegmentUiState()
    data class Error(val message: String) : SegmentUiState()
}

class SegmentViewModel : ViewModel() {

    private val _state = MutableStateFlow<SegmentUiState>(SegmentUiState.Idle)
    val state: StateFlow<SegmentUiState> = _state

    private val _actionMessage = MutableStateFlow<String?>(null)
    val actionMessage: StateFlow<String?> = _actionMessage

    fun loadSegments() {
        _state.value = SegmentUiState.Loading
        viewModelScope.launch {
            try {
                val segments = ApiClient.api.getSegments()
                _state.value = SegmentUiState.Success(segments)
            } catch (e: Exception) {
                // Logar o erro para depuração
                android.util.Log.e("SEGMENT_ERROR", "Erro ao carregar segmentos: ${e.message}")

                val errorMessage = if (e.message?.contains("403") == true) {
                    "Acesso negado aos segmentos. Verifique suas permissões."
                } else {
                    "Erro ao carregar segmentos."
                }
                _state.value = SegmentUiState.Error(errorMessage)
            }
        }
    }

    fun createSegment(nome: String, descricao: String?, tags: List<String>) {
        viewModelScope.launch {
            try {
                ApiClient.api.createSegment(CreateSegmentRequest(nome, descricao, tags))
                _actionMessage.value = "Segmento criado."
                loadSegments()
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao criar segmento."
            }
        }
    }

    fun deleteSegment(id: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.deleteSegment(id)
                _actionMessage.value = "Segmento removido."
                loadSegments()
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao remover segmento."
            }
        }
    }

    fun addClientToSegment(segmentId: String, clienteId: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.addClientToSegment(segmentId, AddClientToSegmentRequest(clienteId))
                _actionMessage.value = "Cliente adicionado ao segmento."
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao adicionar cliente."
            }
        }
    }

    fun removeClientFromSegment(segmentId: String, clienteId: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.removeClientFromSegment(segmentId, clienteId)
                _actionMessage.value = "Cliente removido do segmento."
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao remover cliente."
            }
        }
    }

    fun clearMessage() { _actionMessage.value = null }
}

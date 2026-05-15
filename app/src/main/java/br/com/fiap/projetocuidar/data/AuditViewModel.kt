package br.com.fiap.projetocuidar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.ApiClient
import br.com.fiap.projetocuidar.data.network.AuditLogResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuditUiState {
    object Idle : AuditUiState()
    object Loading : AuditUiState()
    data class Success(val logs: List<AuditLogResponse>) : AuditUiState()
    data class Error(val message: String) : AuditUiState()
}

class AuditViewModel : ViewModel() {

    private val _state = MutableStateFlow<AuditUiState>(AuditUiState.Idle)
    val state: StateFlow<AuditUiState> = _state

    fun loadLogs(
        userId: String? = null,
        acao: String? = null,
        from: String? = null,
        to: String? = null,
        page: Int = 0
    ) {
        _state.value = AuditUiState.Loading
        viewModelScope.launch {
            try {
                val paged = ApiClient.api.getAuditLogs(userId, acao, from, to, page)
                _state.value = AuditUiState.Success(paged.content)
            } catch (e: Exception) {
                _state.value = AuditUiState.Error("Erro ao carregar logs de auditoria.")
            }
        }
    }
}

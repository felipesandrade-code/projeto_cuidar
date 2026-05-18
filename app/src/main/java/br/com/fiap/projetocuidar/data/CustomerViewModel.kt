package br.com.fiap.projetocuidar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.ApiClient
import br.com.fiap.projetocuidar.data.network.CustomerResponse
import br.com.fiap.projetocuidar.data.network.CustomerTimeline
import br.com.fiap.projetocuidar.data.network.NoteRequest
import br.com.fiap.projetocuidar.data.network.UpdateCustomerRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CustomerUiState {
    object Idle : CustomerUiState()
    object Loading : CustomerUiState()
    data class Success(val customers: List<CustomerResponse>) : CustomerUiState()
    data class Error(val message: String) : CustomerUiState()
}

sealed class TimelineState {
    object Idle : TimelineState()
    object Loading : TimelineState()
    data class Success(val timeline: CustomerTimeline) : TimelineState()
    data class Error(val message: String) : TimelineState()
}

class CustomerViewModel : ViewModel() {

    private val _state = MutableStateFlow<CustomerUiState>(CustomerUiState.Idle)
    val state: StateFlow<CustomerUiState> = _state

    private val _timelineState = MutableStateFlow<TimelineState>(TimelineState.Idle)
    val timelineState: StateFlow<TimelineState> = _timelineState

    private val _selectedCustomer = MutableStateFlow<CustomerResponse?>(null)
    val selectedCustomer: StateFlow<CustomerResponse?> = _selectedCustomer

    private val _actionMessage = MutableStateFlow<String?>(null)
    val actionMessage: StateFlow<String?> = _actionMessage

    fun selectCustomer(customer: CustomerResponse) {
        _selectedCustomer.value = customer
    }

    fun loadCustomers(
        search: String? = null,
        tag: String? = null,
        status: String? = null,
        page: Int = 0
    ) {
        _state.value = CustomerUiState.Loading
        viewModelScope.launch {
            try {
                val paged = ApiClient.api.getCustomers(search = search, tag = tag, status = status, page = page)
                _state.value = CustomerUiState.Success(paged.content)
            } catch (e: Exception) {
                _state.value = CustomerUiState.Error("Erro ao carregar clientes.")
            }
        }
    }

    fun loadTimeline(customerId: String) {
        _timelineState.value = TimelineState.Loading
        viewModelScope.launch {
            try {
                val timeline = ApiClient.api.getCustomerTimeline(customerId)
                _timelineState.value = TimelineState.Success(timeline)
            } catch (e: Exception) {
                _timelineState.value = TimelineState.Error("Erro ao carregar timeline.")
            }
        }
    }

    fun updateCustomer(id: String, tags: List<String>? = null, score: Int? = null, status: String? = null) {
        viewModelScope.launch {
            try {
                ApiClient.api.updateCustomer(id, UpdateCustomerRequest(tags, score, status))
                _actionMessage.value = "Cliente atualizado com sucesso."
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao atualizar cliente."
            }
        }
    }

    fun addNote(customerId: String, texto: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.addNote(customerId, NoteRequest(texto))
                _actionMessage.value = "Nota adicionada."
                loadTimeline(customerId)
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao adicionar nota."
            }
        }
    }

    fun clearMessage() { _actionMessage.value = null }
}

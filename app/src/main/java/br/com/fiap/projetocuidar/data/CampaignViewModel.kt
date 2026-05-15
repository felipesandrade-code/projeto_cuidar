package br.com.fiap.projetocuidar.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.ApiClient
import br.com.fiap.projetocuidar.data.network.CampaignRequest
import br.com.fiap.projetocuidar.data.network.CampaignResponse
import br.com.fiap.projetocuidar.data.network.ScheduleCampaignRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CampaignUiState {
    object Idle : CampaignUiState()
    object Loading : CampaignUiState()
    data class Success(val campaigns: List<CampaignResponse>) : CampaignUiState()
    data class Error(val message: String) : CampaignUiState()
}

class CampaignViewModel : ViewModel() {

    private val _state = MutableStateFlow<CampaignUiState>(CampaignUiState.Idle)
    val state: StateFlow<CampaignUiState> = _state

    private val _actionMessage = MutableStateFlow<String?>(null)
    val actionMessage: StateFlow<String?> = _actionMessage

    fun loadCampaigns(status: String? = null) {
        _state.value = CampaignUiState.Loading
        viewModelScope.launch {
            try {
                val list = ApiClient.api.getCampaigns(status)
                _state.value = CampaignUiState.Success(list)
            } catch (e: Exception) {
                _state.value = CampaignUiState.Error("Erro ao carregar campanhas.")
            }
        }
    }

    fun createCampaign(title: String, body: String, segmentoId: String?, url: String? = null, deeplink: String? = null) {
        viewModelScope.launch {
            try {
                ApiClient.api.createCampaign(CampaignRequest(title, body, url, deeplink, segmentoId = segmentoId))
                _actionMessage.value = "Campanha criada."
                loadCampaigns()
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao criar campanha."
            }
        }
    }

    fun sendCampaign(id: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.sendCampaign(id)
                _actionMessage.value = "Campanha enviada via FCM."
                loadCampaigns()
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao enviar campanha."
            }
        }
    }

    fun scheduleCampaign(id: String, scheduledAt: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.scheduleCampaign(id, ScheduleCampaignRequest(scheduledAt))
                _actionMessage.value = "Campanha agendada."
                loadCampaigns()
            } catch (e: Exception) {
                _actionMessage.value = "Erro ao agendar campanha."
            }
        }
    }

    fun clearMessage() { _actionMessage.value = null }
}

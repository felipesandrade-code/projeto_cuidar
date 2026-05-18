package br.com.fiap.projetocuidar.data.network

data class RegisterRequest(
    val nome: String,
    val sobrenome: String,
    val email: String,
    val senha: String,
    val telefone: String? = null,
    val role: String = "CLIENT"
)

data class LoginRequest(val email: String, val senha: String)

data class UserResponse(
    val id: String,
    val email: String,
    val nome: String,
    val sobrenome: String,
    val role: String,
    val status: String
)

data class AuthResponse(val token: String, val user: UserResponse)

data class OrphanageApiModel(
    val id: String? = null,
    val nome: String,
    val categoria: String,
    val telefone: String,
    val sobre: String? = null,
    val fotoUrl: String? = null,
    val instrucaoVisita: String? = null,
    val horarioVisita: String? = null,
    val fimDeSemana: Boolean = false,
    val lat: Double,
    val lng: Double,
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val distanciaKm: Double? = null
)

data class CustomerResponse(
    val id: String,
    val nome: String,
    val sobrenome: String,
    val email: String,
    val telefone: String? = null,
    val tags: List<String> = emptyList(),
    val score: Int = 0,
    val status: String,
    val fcmToken: String? = null,
    val createdAt: String? = null
)

data class UpdateCustomerRequest(
    val tags: List<String>? = null,
    val score: Int? = null,
    val status: String? = null
)

data class FcmTokenRequest(val fcmToken: String)
data class NoteRequest(val texto: String)

data class CustomerTimeline(
    val cliente: CustomerResponse,
    val ultimasMensagens: List<MessageResponse> = emptyList(),
    val ultimasCampanhasRecebidas: List<CampaignResponse> = emptyList(),
    val segmentos: List<SegmentResponse> = emptyList(),
    val notas: List<String> = emptyList()
)

data class PagedCustomers(
    val content: List<CustomerResponse>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
)

data class PagedMessages(
    val content: List<MessageResponse>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
)

data class PagedAuditLogs(
    val content: List<AuditLogResponse>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
)

data class SegmentResponse(
    val id: String,
    val nome: String,
    val descricao: String? = null,
    val tags: List<String> = emptyList()
)

data class CreateSegmentRequest(
    val nome: String,
    val descricao: String? = null,
    val tags: List<String> = emptyList()
)

data class AddClientToSegmentRequest(val clienteId: String)

data class MessageRequest(
    val remetente: String? = null,
    val destinatario: String? = null,
    val segmentoId: String? = null,
    val tipo: String = "TEXT",
    val conteudo: String
)

data class MessageResponse(
    val id: String,
    val remetente: String? = null,
    val destinatario: String? = null,
    val segmentoId: String? = null,
    val tipo: String,
    val conteudo: String,
    val status: String,
    val createdAt: String? = null
)

data class UpdateMessageStatusRequest(val status: String)

data class CampaignActionModel(val action: String, val title: String)

data class CampaignRequest(
    val title: String,
    val body: String,
    val url: String? = null,
    val deeplink: String? = null,
    val actions: List<CampaignActionModel> = emptyList(),
    val actionUrls: Map<String, String> = emptyMap(),
    val segmentoId: String? = null
)

data class CampaignResponse(
    val id: String,
    val title: String,
    val body: String,
    val url: String? = null,
    val deeplink: String? = null,
    val actions: List<CampaignActionModel> = emptyList(),
    val segmentoId: String? = null,
    val status: String,
    val scheduledAt: String? = null
)

data class ScheduleCampaignRequest(val scheduledAt: String)

data class AuditLogResponse(
    val id: String,
    val userId: String? = null,
    val role: String? = null,
    val acao: String,
    val recurso: String? = null,
    val recursoId: String? = null,
    val details: String? = null,
    val ip: String? = null,
    val timestamp: String? = null
)

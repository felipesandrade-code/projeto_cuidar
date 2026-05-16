package br.com.fiap.projetocuidar.data.network

import retrofit2.http.*

interface ApiService {

    // Auth
    @POST("api/auth/register")
    suspend fun register(@Body req: RegisterRequest): AuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body req: LoginRequest): AuthResponse

    @GET("api/auth/me")
    suspend fun me(): AuthResponse

    // Orfanatos
    @GET("api/orfanatos")
    suspend fun getOrfanatos(
        @Query("categoria") categoria: String? = null,
        @Query("fimDeSemana") fimDeSemana: Boolean? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): List<OrphanageApiModel>

    @GET("api/orfanatos/proximos")
    suspend fun getProximos(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("raio") raio: Double = 10.0
    ): List<OrphanageApiModel>

    @GET("api/orfanatos/{id}")
    suspend fun getOrfanato(@Path("id") id: String): OrphanageApiModel

    @POST("api/orfanatos")
    suspend fun createOrfanato(@Body req: OrphanageApiModel): OrphanageApiModel

    @PUT("api/orfanatos/{id}")
    suspend fun updateOrfanato(@Path("id") id: String, @Body req: OrphanageApiModel): OrphanageApiModel

    @DELETE("api/orfanatos/{id}")
    suspend fun deleteOrfanato(@Path("id") id: String)

    // Customers
    @GET("api/customers")
    suspend fun getCustomers(
        @Query("search") search: String? = null,
        @Query("tag") tag: String? = null,
        @Query("status") status: String? = null,
        @Query("scoreMin") scoreMin: Int? = null,
        @Query("scoreMax") scoreMax: Int? = null,
        @Query("segmentoId") segmentoId: String? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): PagedCustomers

    @PATCH("api/customers/{id}")
    suspend fun updateCustomer(@Path("id") id: String, @Body req: UpdateCustomerRequest): CustomerResponse

    @PUT("api/customers/{id}/fcm-token")
    suspend fun updateFcmToken(@Path("id") id: String, @Body req: FcmTokenRequest)

    @GET("api/customers/{id}/timeline")
    suspend fun getCustomerTimeline(@Path("id") id: String): CustomerTimeline

    @POST("api/customers/{id}/notes")
    suspend fun addNote(@Path("id") id: String, @Body req: NoteRequest)

    @GET("api/customers/{id}/notes")
    suspend fun getNotes(@Path("id") id: String): List<String>

    // Segments
    @GET("api/segments")
    suspend fun getSegments(): List<SegmentResponse>

    @POST("api/segments")
    suspend fun createSegment(@Body req: CreateSegmentRequest): SegmentResponse

    @GET("api/segments/{id}")
    suspend fun getSegment(@Path("id") id: String): SegmentResponse

    @PUT("api/segments/{id}")
    suspend fun updateSegment(@Path("id") id: String, @Body req: CreateSegmentRequest): SegmentResponse

    @DELETE("api/segments/{id}")
    suspend fun deleteSegment(@Path("id") id: String)

    @POST("api/segments/{id}/clients")
    suspend fun addClientToSegment(@Path("id") id: String, @Body req: AddClientToSegmentRequest)

    @DELETE("api/segments/{id}/clients/{clienteId}")
    suspend fun removeClientFromSegment(@Path("id") id: String, @Path("clienteId") clienteId: String)

    // Messages
    @POST("api/messages")
    suspend fun sendMessage(@Body req: MessageRequest): List<MessageResponse>

    @GET("api/inbox/{clienteId}")
    suspend fun getInbox(
        @Path("clienteId") clienteId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): PagedMessages

    @GET("api/messages/{id}")
    suspend fun getMessageById(@Path("id") id: String): MessageResponse

    @PATCH("api/messages/{id}/status")
    suspend fun updateMessageStatus(@Path("id") id: String, @Body req: UpdateMessageStatusRequest)

    // Campaigns
    @POST("api/campaigns")
    suspend fun createCampaign(@Body req: CampaignRequest): CampaignResponse

    @GET("api/campaigns")
    suspend fun getCampaigns(@Query("status") status: String? = null): List<CampaignResponse>

    @GET("api/campaigns/{id}")
    suspend fun getCampaign(@Path("id") id: String): CampaignResponse

    @POST("api/campaigns/{id}/send")
    suspend fun sendCampaign(@Path("id") id: String): CampaignResponse

    @POST("api/campaigns/{id}/schedule")
    suspend fun scheduleCampaign(@Path("id") id: String, @Body req: ScheduleCampaignRequest): CampaignResponse

    // Audit Logs
    @GET("api/audit-logs")
    suspend fun getAuditLogs(
        @Query("userId") userId: String? = null,
        @Query("acao") acao: String? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): PagedAuditLogs
}

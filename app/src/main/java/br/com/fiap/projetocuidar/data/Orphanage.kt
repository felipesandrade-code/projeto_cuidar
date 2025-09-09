package br.com.fiap.projetocuidar.data

data class Orphanage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nome: String,
    val categoria: String,
    val telefone: String,
    val sobre: String?,
    val fotoUrl: String?,
    val instrucaoVisita: String?,
    val horarioVisita: String?,
    val fimDeSemana: Boolean,
    val lat: Double,
    val lng: Double
)

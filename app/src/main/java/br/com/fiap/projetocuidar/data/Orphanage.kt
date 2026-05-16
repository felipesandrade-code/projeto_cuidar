package br.com.fiap.projetocuidar.data

import br.com.fiap.projetocuidar.data.network.OrphanageApiModel

data class Orphanage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nome: String,
    val categoria: String,
    val telefone: String,
    val sobre: String? = null,
    val fotoUrl: String? = null,
    val instrucaoVisita: String? = null,
    val horarioVisita: String? = null,
    val fimDeSemana: Boolean,
    val lat: Double,
    val lng: Double,
    val createdBy: String? = null,
    val createdAt: String? = null,
    val distanciaKm: Double? = null
)

fun OrphanageApiModel.toOrphanage() = Orphanage(
    id = id ?: java.util.UUID.randomUUID().toString(),
    nome = nome,
    categoria = categoria,
    telefone = telefone,
    sobre = sobre,
    fotoUrl = fotoUrl,
    instrucaoVisita = instrucaoVisita,
    horarioVisita = horarioVisita,
    fimDeSemana = fimDeSemana,
    lat = lat,
    lng = lng,
    createdBy = createdBy,
    createdAt = createdAt,
    distanciaKm = distanciaKm
)

fun Orphanage.toApiModel() = OrphanageApiModel(
    id = id,
    nome = nome,
    categoria = categoria,
    telefone = telefone,
    sobre = sobre,
    fotoUrl = fotoUrl,
    instrucaoVisita = instrucaoVisita,
    horarioVisita = horarioVisita,
    fimDeSemana = fimDeSemana,
    lat = lat,
    lng = lng,
    createdBy = createdBy
)

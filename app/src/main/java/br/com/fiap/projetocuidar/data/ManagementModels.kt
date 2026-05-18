package br.com.fiap.projetocuidar.data

import java.util.UUID

data class Donation(
    val id: String = UUID.randomUUID().toString(),
    val orphanageId: String,
    val donorName: String,
    val type: String,
    val value: String? = null,
    val message: String? = null,
    val date: String = "16/05/2026"
)

data class Volunteer(
    val id: String = UUID.randomUUID().toString(),
    val userId: String? = null, // ID do usuário no backend
    val orphanageId: String,
    val name: String,
    val email: String,
    val task: String,
    val taskId: String? = null, // Vínculo com uma tarefa específica
    val isAvailable: Boolean = true,
    val dateJoined: String = "15/05/2026"
)

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val orphanageId: String,
    val title: String,
    val description: String,
    val area: String,
    val date: String,
    val status: String = "OPEN" // OPEN, FILLED, COMPLETED
)

data class News(
    val id: String = UUID.randomUUID().toString(),
    val orphanageId: String,
    val orphanageName: String,
    val title: String,
    val content: String,
    val imageUrl: String? = null,
    val date: String = "17/05/2026"
)

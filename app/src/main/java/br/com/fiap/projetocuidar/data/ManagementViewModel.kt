package br.com.fiap.projetocuidar.data

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class ManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val storage = ManagementStorage(application, moshi)

    private val _donations = mutableStateListOf<Donation>()
    val donations: List<Donation> get() = _donations

    private val _volunteers = mutableStateListOf<Volunteer>()
    val volunteers: List<Volunteer> get() = _volunteers

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    private val _news = mutableStateListOf<News>()
    val news: List<News> get() = _news

    init {
        loadFromStorage()
    }

    private fun loadFromStorage() {
        viewModelScope.launch {
            val savedDonations = storage.loadDonations()
            val savedVolunteers = storage.loadVolunteers()
            val savedTasks = storage.loadTasks()
            val savedNews = storage.loadNews()

            if (savedDonations.isEmpty() && savedVolunteers.isEmpty() && savedTasks.isEmpty() && savedNews.isEmpty()) {
                setupMockData()
            } else {
                _donations.addAll(savedDonations)
                _volunteers.addAll(savedVolunteers)
                _tasks.addAll(savedTasks)
                _news.addAll(savedNews)
            }
        }
    }

    private fun setupMockData() {
        addDonation(Donation(orphanageId = "1", donorName = "João Silva", type = "Dinheiro", value = "R$ 50,00", message = "Espero que ajude!"))
        addDonation(Donation(orphanageId = "1", donorName = "Maria Souza", type = "Alimentos", value = "5kg de Arroz", message = "Com carinho."))
        
        addVolunteer(Volunteer(orphanageId = "1", userId = "u1", name = "Pedro Santos", email = "pedro@email.com", task = "Educação", isAvailable = true))
        addVolunteer(Volunteer(orphanageId = "1", userId = "u2", name = "Ana Lima", email = "ana@email.com", task = "Artes", isAvailable = true))
        
        addTask(Task(orphanageId = "1", title = "Pintura do refeitório", description = "Precisamos de voluntários para ajudar a pintar o refeitório principal.", area = "Infraestrutura", date = "20/05/2026"))
        addTask(Task(orphanageId = "1", title = "Aula de Inglês Básico", description = "Vaga para ensinar o alfabeto e cores para crianças de 6 a 8 anos.", area = "Educação", date = "22/05/2026"))

        addNews(News(orphanageId = "1", orphanageName = "Orfanato Teste", title = "Nova ala inaugurada!", content = "Graças às doações, conseguimos abrir a nova sala de informática."))
    }

    fun addDonation(donation: Donation) {
        _donations.add(0, donation)
        persistDonations()
    }

    fun addVolunteer(volunteer: Volunteer) {
        if (_volunteers.none { it.email == volunteer.email && it.orphanageId == volunteer.orphanageId }) {
            _volunteers.add(0, volunteer)
            persistVolunteers()
        }
    }

    fun updateVolunteerAvailability(email: String, isAvailable: Boolean) {
        val index = _volunteers.indexOfFirst { it.email.lowercase() == email.lowercase() }
        if (index != -1) {
            val updated = _volunteers[index].copy(isAvailable = isAvailable)
            _volunteers[index] = updated
            persistVolunteers()
        }
    }

    fun addTask(task: Task) {
        _tasks.add(0, task)
        persistTasks()
    }

    fun applyForTask(volunteerEmail: String, taskId: String) {
        val index = _volunteers.indexOfFirst { it.email.lowercase() == volunteerEmail.lowercase() }
        if (index != -1) {
            val updated = _volunteers[index].copy(taskId = taskId)
            _volunteers[index] = updated
            persistVolunteers()
        }
    }

    fun addNews(news: News) {
        _news.add(0, news)
        persistNews()
    }

    fun deleteNews(newsId: String) {
        _news.removeAll { it.id == newsId }
        persistNews()
    }

    fun getDonationsForOrphanage(orphanageId: String): List<Donation> {
        return _donations.filter { it.orphanageId == orphanageId || orphanageId == "" }
    }

    fun getVolunteersForOrphanage(orphanageId: String): List<Volunteer> {
        return _volunteers.filter { it.orphanageId == orphanageId || orphanageId == "" }
    }

    fun getTasksForOrphanage(orphanageId: String): List<Task> {
        return _tasks.filter { it.orphanageId == orphanageId || orphanageId == "" }
    }

    fun getNewsForOrphanage(orphanageId: String): List<News> {
        return _news.filter { it.orphanageId == orphanageId || orphanageId == "" }
    }

    private fun persistDonations() = viewModelScope.launch { storage.saveDonations(_donations.toList()) }
    private fun persistVolunteers() = viewModelScope.launch { storage.saveVolunteers(_volunteers.toList()) }
    private fun persistTasks() = viewModelScope.launch { storage.saveTasks(_tasks.toList()) }
    private fun persistNews() = viewModelScope.launch { storage.saveNews(_news.toList()) }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ManagementViewModel(app) as T
        }
    }
}

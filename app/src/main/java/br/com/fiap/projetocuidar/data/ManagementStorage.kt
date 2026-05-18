package br.com.fiap.projetocuidar.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.managementDataStore by preferencesDataStore(name = "management_data")

class ManagementStorage(private val context: Context, private val moshi: Moshi) {

    private val KEY_DONATIONS = stringPreferencesKey("donations_json")
    private val KEY_VOLUNTEERS = stringPreferencesKey("volunteers_json")
    private val KEY_TASKS = stringPreferencesKey("tasks_json")
    private val KEY_NEWS = stringPreferencesKey("news_json")

    private val donationType = Types.newParameterizedType(List::class.java, Donation::class.java)
    private val volunteerType = Types.newParameterizedType(List::class.java, Volunteer::class.java)
    private val taskType = Types.newParameterizedType(List::class.java, Task::class.java)
    private val newsType = Types.newParameterizedType(List::class.java, News::class.java)

    suspend fun loadDonations(): List<Donation> {
        val json = context.managementDataStore.data.map { it[KEY_DONATIONS] ?: "" }.first()
        return if (json.isBlank()) emptyList() else moshi.adapter<List<Donation>>(donationType).fromJson(json) ?: emptyList()
    }

    suspend fun saveDonations(list: List<Donation>) {
        val json = moshi.adapter<List<Donation>>(donationType).toJson(list)
        context.managementDataStore.edit { it[KEY_DONATIONS] = json }
    }

    suspend fun loadVolunteers(): List<Volunteer> {
        val json = context.managementDataStore.data.map { it[KEY_VOLUNTEERS] ?: "" }.first()
        return if (json.isBlank()) emptyList() else moshi.adapter<List<Volunteer>>(volunteerType).fromJson(json) ?: emptyList()
    }

    suspend fun saveVolunteers(list: List<Volunteer>) {
        val json = moshi.adapter<List<Volunteer>>(volunteerType).toJson(list)
        context.managementDataStore.edit { it[KEY_VOLUNTEERS] = json }
    }

    suspend fun loadTasks(): List<Task> {
        val json = context.managementDataStore.data.map { it[KEY_TASKS] ?: "" }.first()
        return if (json.isBlank()) emptyList() else moshi.adapter<List<Task>>(taskType).fromJson(json) ?: emptyList()
    }

    suspend fun saveTasks(list: List<Task>) {
        val json = moshi.adapter<List<Task>>(taskType).toJson(list)
        context.managementDataStore.edit { it[KEY_TASKS] = json }
    }

    suspend fun loadNews(): List<News> {
        val json = context.managementDataStore.data.map { it[KEY_NEWS] ?: "" }.first()
        return if (json.isBlank()) emptyList() else moshi.adapter<List<News>>(newsType).fromJson(json) ?: emptyList()
    }

    suspend fun saveNews(list: List<News>) {
        val json = moshi.adapter<List<News>>(newsType).toJson(list)
        context.managementDataStore.edit { it[KEY_NEWS] = json }
    }
}

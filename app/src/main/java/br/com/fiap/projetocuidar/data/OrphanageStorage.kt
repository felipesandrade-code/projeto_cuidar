package br.com.fiap.projetocuidar.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "orphanages")
private val KEY_JSON = stringPreferencesKey("orphanages_json")

class OrphanageStorage(private val context: Context, private val moshi: Moshi) {

    private val type = Types.newParameterizedType(List::class.java, Orphanage::class.java)
    private val adapter = moshi.adapter<List<Orphanage>>(type)

    suspend fun load(): List<Orphanage> {
        val json = context.dataStore.data
            .map { prefs -> prefs[KEY_JSON] ?: "" }
            .first()
        if (json.isBlank()) return emptyList()
        return runCatching { adapter.fromJson(json) ?: emptyList() }.getOrDefault(emptyList())
    }

    suspend fun save(list: List<Orphanage>) {
        val json = runCatching { adapter.toJson(list) }.getOrDefault("[]")
        context.dataStore.edit { prefs ->
            prefs[KEY_JSON] = json
        }
    }
}

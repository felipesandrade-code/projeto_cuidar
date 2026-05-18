package br.com.fiap.projetocuidar.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.fiap.projetocuidar.data.network.MessageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.messageDataStore by preferencesDataStore(name = "messages_data")

class MessageStorage(private val context: Context, private val moshi: Moshi) {

    private val KEY_MESSAGES = stringPreferencesKey("sent_messages_json")
    private val messageListType = Types.newParameterizedType(List::class.java, MessageResponse::class.java)

    suspend fun loadMessages(): List<MessageResponse> {
        val json = context.messageDataStore.data.map { it[KEY_MESSAGES] ?: "" }.first()
        return if (json.isBlank()) emptyList() 
        else moshi.adapter<List<MessageResponse>>(messageListType).fromJson(json) ?: emptyList()
    }

    suspend fun saveMessages(list: List<MessageResponse>) {
        val json = moshi.adapter<List<MessageResponse>>(messageListType).toJson(list)
        context.messageDataStore.edit { it[KEY_MESSAGES] = json }
    }
    
    suspend fun clear() {
        context.messageDataStore.edit { it.clear() }
    }
}

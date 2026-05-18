package br.com.fiap.projetocuidar.data.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("jwt_token")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_NOME = stringPreferencesKey("user_nome")
        private val KEY_USER_SOBRENOME = stringPreferencesKey("user_sobrenome")
        private val KEY_USER_ROLE = stringPreferencesKey("user_role")
        private val KEY_USER_TYPE = stringPreferencesKey("user_type")
        private val KEY_USER_PHONE = stringPreferencesKey("user_phone")
        private val KEY_USER_CPF_CNPJ = stringPreferencesKey("user_cpf_cnpj")
        private val KEY_USER_FOTO = stringPreferencesKey("user_foto")
        
        // Chave base para mapear email -> tipo
        private fun typeKey(email: String) = stringPreferencesKey("type_$email")
    }

    suspend fun saveSession(token: String, user: UserResponse, customType: String? = null) {
        context.authDataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = user.id
            prefs[KEY_USER_EMAIL] = user.email
            prefs[KEY_USER_NOME] = user.nome
            prefs[KEY_USER_SOBRENOME] = user.sobrenome
            prefs[KEY_USER_ROLE] = user.role
            
            // Se recebemos um tipo (no registro ou se já tínhamos), salvamos
            val typeToSave = customType ?: prefs[typeKey(user.email)]
            if (typeToSave != null) {
                prefs[KEY_USER_TYPE] = typeToSave
                prefs[typeKey(user.email)] = typeToSave
            }
        }
    }

    suspend fun updateLocalUser(phone: String, cpfCnpj: String, fotoUrl: String?, type: String, role: String) {
        context.authDataStore.edit { prefs ->
            prefs[KEY_USER_PHONE] = phone
            prefs[KEY_USER_CPF_CNPJ] = cpfCnpj
            prefs[KEY_USER_TYPE] = type
            prefs[KEY_USER_ROLE] = role
            if (fotoUrl != null) prefs[KEY_USER_FOTO] = fotoUrl else prefs.remove(KEY_USER_FOTO)
        }
    }

    suspend fun getExtraData(): Triple<String, String, String?> {
        val prefs = context.authDataStore.data.first()
        return Triple(
            prefs[KEY_USER_PHONE] ?: "",
            prefs[KEY_USER_CPF_CNPJ] ?: "",
            prefs[KEY_USER_FOTO]
        )
    }

    suspend fun getUserType(email: String? = null): String? {
        val prefs = context.authDataStore.data.first()
        return if (email != null) {
            prefs[typeKey(email)] ?: prefs[KEY_USER_TYPE]
        } else {
            prefs[KEY_USER_TYPE]
        }
    }

    suspend fun getToken(): String? =
        context.authDataStore.data.map { it[KEY_TOKEN] }.first()

    suspend fun getSavedUser(): UserResponse? {
        val prefs = context.authDataStore.data.first()
        val id = prefs[KEY_USER_ID] ?: return null
        val email = prefs[KEY_USER_EMAIL] ?: return null
        val nome = prefs[KEY_USER_NOME] ?: return null
        val sobrenome = prefs[KEY_USER_SOBRENOME] ?: ""
        val role = prefs[KEY_USER_ROLE] ?: "CLIENT"
        return UserResponse(id, email, nome, sobrenome, role, "ACTIVE")
    }

    suspend fun clear() {
        context.authDataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
            prefs.remove(KEY_USER_ID)
            prefs.remove(KEY_USER_EMAIL)
            prefs.remove(KEY_USER_NOME)
            prefs.remove(KEY_USER_SOBRENOME)
            prefs.remove(KEY_USER_ROLE)
            prefs.remove(KEY_USER_TYPE)
        }
    }
}

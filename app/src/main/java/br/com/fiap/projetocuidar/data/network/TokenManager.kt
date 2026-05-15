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
    }

    suspend fun saveSession(token: String, user: UserResponse) {
        context.authDataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = user.id
            prefs[KEY_USER_EMAIL] = user.email
            prefs[KEY_USER_NOME] = user.nome
            prefs[KEY_USER_SOBRENOME] = user.sobrenome
            prefs[KEY_USER_ROLE] = user.role
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
        context.authDataStore.edit { it.clear() }
    }
}

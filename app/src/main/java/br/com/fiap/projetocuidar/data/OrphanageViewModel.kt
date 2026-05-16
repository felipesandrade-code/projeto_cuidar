package br.com.fiap.projetocuidar.data

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.projetocuidar.data.network.ApiClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

data class PlacePreview(val name: String, val lat: Double, val lng: Double)

class OrphanageViewModel(app: Application) : AndroidViewModel(app) {

    val selectedLat = mutableStateOf<Double?>(null)
    val selectedLng = mutableStateOf<Double?>(null)

    val orphanages = mutableStateListOf<Orphanage>()
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val selectedOrphanage = mutableStateOf<Orphanage?>(null)
    val selectedPlace = mutableStateOf<PlacePreview?>(null)

    private val storage = OrphanageStorage(
        app.applicationContext,
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    )

    fun loadOrphanages() {
        viewModelScope.launch {
            if (!ApiClient.hasToken()) {
                val saved = storage.load()
                if (saved.isNotEmpty()) {
                    orphanages.clear()
                    orphanages.addAll(saved)
                }
                return@launch
            }
            isLoading.value = true
            error.value = null
            try {
                val list = ApiClient.api.getOrfanatos()
                orphanages.clear()
                orphanages.addAll(list.map { it.toOrphanage() })
                storage.save(orphanages)
            } catch (e: Exception) {
                val saved = storage.load()
                if (saved.isNotEmpty()) {
                    orphanages.clear()
                    orphanages.addAll(saved)
                    error.value = "Usando dados em cache."
                } else {
                    error.value = "Sem conexão com o servidor."
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadProximos(lat: Double, lng: Double, raio: Double = 10.0) {
        viewModelScope.launch {
            if (!ApiClient.hasToken()) return@launch
            isLoading.value = true
            error.value = null
            try {
                val list = ApiClient.api.getProximos(lat, lng, raio)
                orphanages.clear()
                orphanages.addAll(list.map { it.toOrphanage() })
            } catch (e: Exception) {
                error.value = "Erro ao carregar orfanatos próximos."
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setSelected(lat: Double, lng: Double) {
        selectedLat.value = lat
        selectedLng.value = lng
    }

    fun clearSelected() {
        selectedLat.value = null
        selectedLng.value = null
    }

    fun add(orphanage: Orphanage) {
        viewModelScope.launch {
            try {
                val created = ApiClient.api.createOrfanato(orphanage.toApiModel())
                orphanages.add(0, created.toOrphanage())
                storage.save(orphanages)
            } catch (e: Exception) {
                orphanages.add(0, orphanage)
                storage.save(orphanages)
                error.value = "Salvo localmente. Sem conexão."
            }
        }
    }

    fun selectOrphanageForDetail(o: Orphanage) {
        selectedOrphanage.value = o
        selectedPlace.value = null
    }

    fun selectPlaceForDetail(name: String, lat: Double, lng: Double) {
        selectedPlace.value = PlacePreview(name, lat, lng)
        selectedOrphanage.value = null
    }

    fun delete(id: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.deleteOrfanato(id)
                orphanages.removeAll { it.id == id }
                storage.save(orphanages)
                if (selectedOrphanage.value?.id == id) {
                    selectedOrphanage.value = null
                }
            } catch (e: Exception) {
                error.value = "Erro ao excluir: ${e.message}"
            }
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(OrphanageViewModel::class.java))
            return OrphanageViewModel(app) as T
        }
    }
}

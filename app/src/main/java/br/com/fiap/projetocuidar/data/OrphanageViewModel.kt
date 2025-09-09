package br.com.fiap.projetocuidar.data

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

data class PlacePreview(val name: String, val lat: Double, val lng: Double)

class OrphanageViewModel(app: Application) : AndroidViewModel(app) {

    val selectedLat = mutableStateOf<Double?>(null)
    val selectedLng = mutableStateOf<Double?>(null)

    val orphanages = mutableStateListOf<Orphanage>()

    val selectedOrphanage = mutableStateOf<Orphanage?>(null)
    val selectedPlace = mutableStateOf<PlacePreview?>(null)

    private val storage = OrphanageStorage(
        app.applicationContext,
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )

    init {
        viewModelScope.launch {
            val saved = storage.load()
            orphanages.clear()
            orphanages.addAll(saved)
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
        orphanages.add(0, orphanage)
        persist()
    }

    private fun persist() {
        viewModelScope.launch {
            storage.save(orphanages)
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

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(OrphanageViewModel::class.java))
            return OrphanageViewModel(app) as T
        }
    }
}

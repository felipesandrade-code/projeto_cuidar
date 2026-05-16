package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.SuperiorMapa
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * Versão Minimalista e Estável da tela de seleção de mapa.
 * Removido: fusedClient (GPS), estilos customizados e animações complexas.
 * Objetivo: Resolver o erro DeadObjectException/TransactionTooLargeException.
 */
@Composable
fun MapaSelectScreen(navController: NavController, vm: OrphanageViewModel) {
    // Estado simples para armazenar o ponto clicado
    var pickedLocation by remember { 
        mutableStateOf<LatLng?>(
            vm.selectedLat.value?.let { lat -> 
                vm.selectedLng.value?.let { lng -> LatLng(lat, lng) } 
            }
        )
    }

    // Configuração de câmera fixa inicial para evitar cálculos pesados no carregamento
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-23.5505, -46.6333), 10f) // São Paulo como padrão
    }

    Box(Modifier.fillMaxSize()) {
        // O componente GoogleMap é o que consome mais memória/binder.
        // Mantemos ele com as configurações padrão (default) para máxima estabilidade.
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                pickedLocation = latLng
            }
        ) {
            // Desenha apenas um marcador se houver local selecionado
            pickedLocation?.let { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Local do Orfanato"
                )
            }
        }

        // Barra superior padrão
        SuperiorMapa(navController)

        // Botão de confirmação aparece apenas se houver uma marcação
        if (pickedLocation != null) {
            Button(
                onClick = {
                    try {
                        vm.setSelected(pickedLocation!!.latitude, pickedLocation!!.longitude)
                        navController.popBackStack()
                    } catch (e: Exception) {
                        android.util.Log.e("MAP_STABLE", "Erro ao confirmar local: ${e.message}")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Text("Confirmar Localização")
            }
        }
    }
}

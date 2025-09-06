package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.components.SuperiorMapa
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun MapaScreen(navcontroller: NavController) {
    Box(
        Modifier.fillMaxSize()
    ){
        SuperiorMapa(navcontroller)

        val orfanatos = listOf(
            LatLng(-20.4487, -54.6173) to "Orfanato Esperança",
            LatLng(-22.908010, -47.077942) to "Orfanato amor de cristo"
        )

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(orfanatos.first().first, 12f)
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .size(700.dp)
                .offset(y= 20.dp)
                .align(alignment = Alignment.Center),
            cameraPositionState = cameraPositionState
        ) {
            orfanatos.forEach { (coordenada, titulo) ->
                Marker(
                    state = rememberUpdatedMarkerState(position = coordenada),
                    title = titulo,
                    snippet = "Clique para detalhes"
                )
            }
        }

        LaunchedEffect(orfanatos) {
            val boundsBuilder = LatLngBounds.builder()
            orfanatos.forEach { (coordenada, _) -> boundsBuilder.include(coordenada) }
            val bounds = boundsBuilder.build()

            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 100) // 100 = padding
            )
        }
    }
}

@Preview
@Composable
private fun MapaScreenPreview() {
    MapaScreen(navcontroller = rememberNavController())
}
package br.com.fiap.projetocuidar.Screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.components.SuperiorMapa
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@Composable
fun MapaSelectScreen(navController: NavController, vm: OrphanageViewModel) {
    val context = LocalContext.current
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var hasPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { res ->
        hasPermission =
            res[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    res[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    LaunchedEffect(Unit) {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fine || coarse) hasPermission = true else {
            launcher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-14.2350, -51.9253), 4.5f)
    }

    val scope = rememberCoroutineScope()

    var picked by remember {
        mutableStateOf(vm.selectedLat.value?.let { lat ->
            vm.selectedLng.value?.let { lng -> LatLng(lat, lng) }
        })
    }

    var props by remember { mutableStateOf(MapProperties(isMyLocationEnabled = false)) }
    var ui by remember { mutableStateOf(MapUiSettings(myLocationButtonEnabled = false)) }

    LaunchedEffect(hasPermission) {
        props = props.copy(isMyLocationEnabled = hasPermission)
        ui = ui.copy(myLocationButtonEnabled = hasPermission)
        if (hasPermission) {
            fusedClient.lastLocation.addOnSuccessListener { loc ->
                loc?.let {
                    val me = LatLng(it.latitude, it.longitude)
                    scope.launch {
                        camera.animate(CameraUpdateFactory.newLatLngZoom(me, 15f))
                    }
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 20.dp),
            cameraPositionState = camera,
            properties = props.copy(
                mapStyleOptions = MapStyleOptions(
                    """
                    [
                      {"featureType":"poi","elementType":"all","stylers":[{"visibility":"off"}]},
                      {"featureType":"transit","elementType":"all","stylers":[{"visibility":"off"}]},
                      {"featureType":"administrative","elementType":"labels","stylers":[{"visibility":"off"}]},
                      {"featureType":"road","elementType":"labels","stylers":[{"visibility":"off"}]},
                      {"featureType":"all","elementType":"labels.icon","stylers":[{"visibility":"off"}]}
                    ]
                    """.trimIndent()
                )
            ),
            uiSettings = ui,
            onMapClick = { latLng ->
                picked = latLng
            }
        ) {
            picked?.let { pos ->
                Marker(
                    state = rememberMarkerState(position = pos),
                    title = "Local selecionado",
                    draggable = false
                )
            }
        }

        SuperiorMapa(navController)

        if (picked != null) {
            Button(
                onClick = {
                    vm.setSelected(picked!!.latitude, picked!!.longitude)
                    navController.navigate("registerOng")
                },

                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) {
                Text("Usar este local")
            }
        }
    }
}

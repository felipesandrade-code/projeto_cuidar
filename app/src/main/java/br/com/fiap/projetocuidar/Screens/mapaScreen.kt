package br.com.fiap.projetocuidar.Screens

import androidx.appcompat.content.res.AppCompatResources
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.SuperiorMapa
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.math.cos
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color

@Composable
fun MapaScreen(navcontroller: NavController, vm: OrphanageViewModel) {
    val context = LocalContext.current
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(Unit) {
        runCatching { MapsInitializer.initialize(context, Renderer.LATEST) {} }
        vm.loadOrphanages()
    }

    var hasLocationPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        hasLocationPermission =
            result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    LaunchedEffect(Unit) {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fine || coarse) hasLocationPermission = true else {
            permissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    val placesReady by remember {
        mutableStateOf(
            if (!Places.isInitialized()) {
                try {
                    Places.initialize(context, context.getString(R.string.google_maps_key), java.util.Locale("pt", "BR"))
                    true
                } catch (_: Exception) { false }
            } else true
        )
    }

    val placesClient: PlacesClient? = remember(placesReady) {
        if (placesReady) Places.createClient(context) else null
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-14.2350, -51.9253), 4.5f)
    }
    var mapLoaded by remember { mutableStateOf(false) }
    var userLatLng by remember { mutableStateOf<LatLng?>(null) }

    val minimalistStyleJson = """
        [
          {"featureType":"poi","elementType":"all","stylers":[{"visibility":"off"}]},
          {"featureType":"transit","elementType":"all","stylers":[{"visibility":"off"}]},
          {"featureType":"administrative","elementType":"labels","stylers":[{"visibility":"off"}]},
          {"featureType":"poi.business","elementType":"all","stylers":[{"visibility":"off"}]},
          {"featureType":"poi.park","elementType":"labels","stylers":[{"visibility":"off"}]},
          {"featureType":"water","elementType":"labels","stylers":[{"visibility":"off"}]},
          {"featureType":"all","elementType":"labels.icon","stylers":[{"visibility":"off"}]}
        ]
    """.trimIndent()

    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = false,
                mapStyleOptions = MapStyleOptions(minimalistStyleJson)
            )
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(MapUiSettings(myLocationButtonEnabled = false))
    }

    LaunchedEffect(hasLocationPermission) {
        mapProperties = mapProperties.copy(isMyLocationEnabled = hasLocationPermission)
        mapUiSettings = mapUiSettings.copy(myLocationButtonEnabled = hasLocationPermission)
        if (hasLocationPermission) {
            fusedClient.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    userLatLng = LatLng(loc.latitude, loc.longitude)
                } else {
                    val p = com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
                    fusedClient.getCurrentLocation(p, null).addOnSuccessListener { cur ->
                        if (cur != null) userLatLng = LatLng(cur.latitude, cur.longitude)
                    }
                }
            }
        }
    }

    var placesOrphanages by remember { mutableStateOf<List<Pair<LatLng, String>>>(emptyList()) }
    var placeIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

    LaunchedEffect(Unit) {
        placeIcon = bitmapDescriptorKeepAspect(context, R.drawable.local, 36f)
    }
    LaunchedEffect(mapLoaded) {
        if (mapLoaded && placeIcon == null) {
            placeIcon = bitmapDescriptorKeepAspect(context, R.drawable.local, 36f)
        }
    }

    LaunchedEffect(mapLoaded, placesReady, placesClient, userLatLng) {
        val u = userLatLng ?: return@LaunchedEffect
        val client = placesClient ?: return@LaunchedEffect
        if (!mapLoaded) return@LaunchedEffect

        val queries = listOf("orfanato","abrigo infantil","casa lar","casa da criança","acolhimento infantil","lar da criança")
        val radiiKm = listOf(5.0, 15.0, 30.0, 60.0, 100.0)
        val results = mutableListOf<Pair<LatLng, String>>()

        outer@ for (q in queries) {
            for (r in radiiKm) {
                val b = makeBoundsAround(u, r)
                val token = AutocompleteSessionToken.newInstance()
                val req = FindAutocompletePredictionsRequest.builder()
                    .setQuery(q)
                    .setSessionToken(token)
                    .setLocationBias(RectangularBounds.newInstance(b.southwest, b.northeast))
                    .setCountries(listOf("BR"))
                    .build()

                val predictions = try {
                    client.findAutocompletePredictions(req).await().autocompletePredictions
                } catch (_: Exception) { emptyList() }
                if (predictions.isEmpty()) continue

                for (pred in predictions.take(15)) {
                    val placeReq = FetchPlaceRequest.newInstance(
                        pred.placeId,
                        listOf(Place.Field.LAT_LNG, Place.Field.NAME)
                    )
                    val place = try { client.fetchPlace(placeReq).await().place } catch (_: Exception) { null }
                    val latLng = place?.latLng
                    val name = place?.name
                    if (latLng != null && !name.isNullOrBlank()) {
                        results += latLng to name
                    }
                }
                if (results.isNotEmpty()) break@outer
            }
        }
        placesOrphanages = results.distinctBy { it.first.latitude to it.first.longitude }
    }

    val localOrphanages by remember { derivedStateOf { vm.orphanages.toList() } }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 20.dp),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            onMapLoaded = { mapLoaded = true }
        ) {
            placesOrphanages.forEach { (coord, title) ->
                Marker(
                    state = rememberMarkerState(position = coord),
                    title = title,
                    icon = placeIcon ?: BitmapDescriptorFactory.defaultMarker(),
                    onClick = {
                        vm.selectPlaceForDetail(title, coord.latitude, coord.longitude)
                        navcontroller.navigate("ong_detail")
                        true
                    }
                )
            }

            localOrphanages.forEach { o ->
                Marker(
                    state = rememberMarkerState(position = LatLng(o.lat, o.lng)),
                    title = o.nome,
                    snippet = o.categoria,
                    icon = placeIcon ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                    onClick = {
                        vm.selectOrphanageForDetail(o)
                        navcontroller.navigate("ong_detail")
                        true
                    }
                )
            }
        }

        FoundOrphanagesPill(
            count = placesOrphanages.size + localOrphanages.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-16).dp),
            onAddClick = { navcontroller.navigate("map_select") }
        )

        SuperiorMapa(navcontroller)
    }

    LaunchedEffect(mapLoaded, userLatLng) {
        if (!mapLoaded) return@LaunchedEffect
        userLatLng?.let { cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 14f)) }
    }
}

private fun bitmapDescriptorKeepAspect(
    context: android.content.Context,
    @DrawableRes resId: Int,
    targetWidthDp: Float = 36f
): BitmapDescriptor {
    val drawable: Drawable = AppCompatResources.getDrawable(context, resId)
        ?: return BitmapDescriptorFactory.defaultMarker()

    val density = context.resources.displayMetrics.density
    val targetW = (targetWidthDp * density).toInt().coerceAtLeast(1)

    val iw = drawable.intrinsicWidth
    val ih = drawable.intrinsicHeight
    val srcW = if (iw > 0) iw else targetW
    val srcH = if (ih > 0) ih else targetW

    val ratio = if (srcW > 0) srcH.toFloat() / srcW.toFloat() else 1f
    val targetH = (targetW * ratio).toInt().coerceAtLeast(1)

    val bmp = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return runCatching { BitmapDescriptorFactory.fromBitmap(bmp) }
        .getOrElse { BitmapDescriptorFactory.defaultMarker() }
}

private data class Bounds(val southwest: LatLng, val northeast: LatLng)
private fun makeBoundsAround(center: LatLng, radiusKm: Double): Bounds {
    val lat = center.latitude
    val lng = center.longitude
    val dLat = radiusKm / 111.32
    val dLng = radiusKm / (111.32 * cos(Math.toRadians(lat)).coerceAtLeast(0.0001))
    val sw = LatLng(lat - dLat, lng - dLng)
    val ne = LatLng(lat + dLat, lng + dLng)
    return Bounds(sw, ne)
}

suspend fun <T> Task<T>.await(): T =
    suspendCancellableCoroutine { cont ->
        addOnSuccessListener { cont.resume(it) }
        addOnFailureListener { e -> cont.resumeWith(Result.failure(e)) }
        addOnCanceledListener { cont.cancel() }
    }

@Composable
private fun FoundOrphanagesPill(
    count: Int,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    val corner = 18.dp
    val bg = Color(0xFFFFFFFF)
    val textColor = Color(0xFF5B6B57)
    val addBg = Color(0xFF7A8E56)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .offset(x = 0.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(corner)
                )
                .background(
                    color = bg,
                    shape = RoundedCornerShape(corner)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$count orfanatos encontrados",
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))


                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(addBg, shape = CircleShape)
                        .clickable { onAddClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar ONG",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

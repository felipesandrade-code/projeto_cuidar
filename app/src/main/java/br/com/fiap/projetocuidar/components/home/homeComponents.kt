package br.com.fiap.projetocuidar.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.LogoComponent
import br.com.fiap.projetocuidar.components.TextHomeComponent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun HomeComponents(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                LogoComponent("Logo", 120.dp, 0.dp, 0.dp)
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = colorResource(R.color.cor_registre),
                    modifier = Modifier
                        .size(40.dp)
                        .offset(y = 40.dp,x = -20.dp)
                )
            }
            TextHomeComponent(
                Modifier,
                "Mapa Ong",
                20.sp,
                FontFamily(Font(R.font.poppins_regular)),
                10.dp,
                10.dp
            )
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .offset(x = 30.dp)
            ) {
                val campoGrande = LatLng(-20.4487, -54.6173)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(campoGrande, 12f)
                }
                val markerState = rememberMarkerState(position = campoGrande)
                GoogleMap(
                    modifier = Modifier.fillMaxWidth(), cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = markerState, title = "ONG Exemplo", snippet = "Clique para detalhes"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextHomeComponent(
                Modifier,
                "Orfanatos",
                20.sp,
                FontFamily(Font(R.font.poppins_regular)),
                130.dp,
                10.dp
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier.fillMaxWidth()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(170.dp)
                        .height(170.dp)
                        .offset(x = 2.dp, y = 5.dp)
                ) {
                    Text(
                        text = "teste",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(170.dp)
                        .height(170.dp)
                        .offset(x = -1.dp, y = 5.dp)
                ) {
                    Text(
                        text = "teste",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeComponentsPreview() {
    HomeComponents()
}
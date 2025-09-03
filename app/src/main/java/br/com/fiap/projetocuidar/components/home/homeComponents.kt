package br.com.fiap.projetocuidar.components.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R
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
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = colorResource(R.color.cor_registre),
                    modifier = Modifier
                        .size(40.dp)
                        .offset(y = 20.dp)
                )
            }
            val cornerRadius = 12.dp
            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .offset(x = 5.dp, y = 6.dp)
                    .background(
                        color = colorResource(R.color.cor_card_footer),
                        shape = RoundedCornerShape(cornerRadius)
                    )
            ) {
                Text(
                    text = "Mapa Ongs",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
            }
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
            Row(
                modifier = Modifier
                    .background(color = colorResource(R.color.cor_card_footer))
                    .align(alignment = Alignment.End)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Teste",
                    modifier = Modifier.offset(x = -100.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Notícias",
                    modifier = Modifier.offset(y = 3.dp, x = 140.dp),
                    color = colorResource(R.color.cor_registre),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                )
            }
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
                    Image(
                        painter = painterResource(R.drawable.image),
                        contentDescription = "Teste",
                        modifier = Modifier
                            .offset(y = 30.dp)
                            .size(100.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(10.dp),
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
package br.com.fiap.projetocuidar.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.LogoComponent
import br.com.fiap.projetocuidar.components.TextHomeComponent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun HomeComponents(modifier: Modifier, navController: NavController) {

    var expanded by remember { mutableStateOf(false) }


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

                IconButton(
                    onClick = {  expanded = true },
                    modifier = Modifier.offset(y = 40.dp, x = -20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = colorResource(R.color.cor_registre),
                        modifier = Modifier.size(40.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Tela Inicial") },
                        onClick = {
                            expanded = false
                            navController.navigate("home")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Mapa") },
                        onClick = {
                            expanded = false
                            navController.navigate("mapa")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Orfanato Esperança") },
                        onClick = {
                            expanded = false
                            navController.navigate("orfanatoEsperanca")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Orfanato Amor de Cristo") },
                        onClick = {
                            expanded = false
                            navController.navigate("orfanatoAmorDeCristo")
                        }
                    )
                    DropdownMenuItem(
                        text = {Text("Sair")},
                        onClick = {
                            expanded = false
                            navController.navigate("login")
                        }
                    )
                }

            }
            TextHomeComponent(
                Modifier,
                "Mapa Orfanatos",
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
                val orfanatos = listOf(
                    LatLng(-20.4487, -54.6173) to "Orfanato Esperança",
                    LatLng(-22.908010, -47.077942) to "Orfanato amor de cristo"
                )

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(orfanatos.first().first, 12f)
                }

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        text = "Orf. amor de cristo",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(15.dp),
                        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                    )
                    Image(
                        painter = painterResource(R.drawable.orfanato_amor_de_cristo),
                        contentDescription = "Orfanato amor de cristo",
                        Modifier
                            .size(180.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable(onClick = {navController.navigate("orfanatoAmorDeCristo")})
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
                        text = "Orf. Esperança",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(15.dp),
                        fontFamily = FontFamily(Font(R.font.nunito_semibold))
                    )
                    Image(
                        painter = painterResource(R.drawable.orfanato3),
                        contentDescription = "Orfanato Esperança",
                        Modifier
                            .size(180.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable(onClick = {navController.navigate("orfanatoEsperanca")}),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeComponentsPreview() {
    HomeComponents(Modifier, navController = rememberNavController())
}
package br.com.fiap.projetocuidar.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun HomeComponents(
    modifier: Modifier, 
    navController: NavController, 
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages

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
                    // Menu dinâmico baseado nos orfanatos reais
                    orphanages.take(3).forEach { o ->
                        DropdownMenuItem(
                            text = { Text(o.nome) },
                            onClick = {
                                expanded = false
                                orphanageViewModel.selectOrphanageForDetail(o)
                                navController.navigate("ong_detail")
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = {Text("Sair")},
                        onClick = {
                            expanded = false
                            authViewModel.logout()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

            }
            TextHomeComponent(
                Modifier,
                if (currentUser != null) "Olá, ${currentUser?.nome}" else "Mapa Orfanatos",
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
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                val cameraPositionState = rememberCameraPositionState {
                    if (orphanages.isNotEmpty()) {
                        position = CameraPosition.fromLatLngZoom(LatLng(orphanages[0].lat, orphanages[0].lng), 10f)
                    } else {
                        position = CameraPosition.fromLatLngZoom(LatLng(-23.5505, -46.6333), 10f)
                    }
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    orphanages.forEach { o ->
                        Marker(
                            state = rememberMarkerState(position = LatLng(o.lat, o.lng)),
                            title = o.nome,
                            onClick = {
                                orphanageViewModel.selectOrphanageForDetail(o)
                                navController.navigate("ong_detail")
                                true
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextHomeComponent(
                Modifier,
                "Orfanatos Próximos",
                20.sp,
                FontFamily(Font(R.font.poppins_regular)),
                10.dp,
                10.dp
            )
            Spacer(modifier = Modifier.height(10.dp))
            
            // Lista horizontal real de orfanatos
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(orphanages) { o ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(170.dp)
                            .height(180.dp)
                            .clickable {
                                orphanageViewModel.selectOrphanageForDetail(o)
                                navController.navigate("ong_detail")
                            }
                    ) {
                        Column {
                            Text(
                                text = o.nome,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                                maxLines = 1
                            )
                            Image(
                                painter = painterResource(R.drawable.orfanato3), // Placeholder real
                                contentDescription = o.nome,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview
@Composable
private fun HomeComponentsPreview() {
    HomeComponents(
        modifier = Modifier, 
        navController = rememberNavController(), 
        authViewModel = AuthViewModel(),
        orphanageViewModel = OrphanageViewModel(android.app.Application())
    )
}
*/
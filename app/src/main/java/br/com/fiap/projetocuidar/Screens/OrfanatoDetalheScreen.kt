package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.*
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun OrfanatoDetalheScreen(
    navController: NavController,
    vm: OrphanageViewModel
) {
    val local = vm.selectedOrphanage.value
    val place = vm.selectedPlace.value

    val nome = local?.nome ?: place?.name ?: "Local"
    val lat = local?.lat ?: place?.lat ?: 0.0
    val lng = local?.lng ?: place?.lng ?: 0.0
    val categoria = local?.categoria ?: "—"
    val telefone = local?.telefone ?: "—"
    val sobre = local?.sobre ?: "—"
    val instrucao = local?.instrucaoVisita ?: "—"
    val horario = local?.horarioVisita ?: "—"
    val fimDeSemana = local?.fimDeSemana ?: false

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(R.color.cor_column_registre))
    ) {
        SuperiorOngComponent(Modifier, stringResource(R.string.text_name), navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 70.dp)
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            TextTituloOngComponent(Modifier, nome)

            TextOngComponent(
                Modifier,
                if (sobre.isBlank()) "Sem descrição disponível." else sobre,
                TextAlign.Center,
                padding = 20.dp,
                FontFamily(Font(R.font.nunito_semibold)),
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(260.dp)
                    .height(160.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                val point = LatLng(lat, lng)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(point, 14f)
                }
                val markerState = rememberMarkerState(position = point)
                Box(Modifier.fillMaxSize()) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = markerState,
                            title = nome,
                            snippet = categoria
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextTituloOngComponent(Modifier,"Instruções para visita")
                TextOngComponent(
                    Modifier,
                    if (instrucao.isBlank()) "Sem instruções cadastradas." else instrucao,
                    TextAlign.Center,
                    padding = 5.dp,
                    FontFamily(Font(R.font.nunito_semibold)),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CardComponentOng(
                    modifier = Modifier,
                    painterResource(R.drawable.icon_clock),
                    "Ícone horário",
                    colorResource(R.color.icone_horario),
                    colorResource(R.color.card_horario_orfanato),
                    if (horario.isBlank()) "Horário não informado" else horario,
                    colorResource(R.color.text_horario_orfanato)
                )
                Spacer(modifier = Modifier.width(40.dp))
                CardComponentOng(
                    painter = painterResource(R.drawable.icons_info),
                    contentDescription = "Ícone informação",
                    tint = colorResource(R.color.icone_info),
                    cardColors = colorResource(R.color.card_fim_de_semana),
                    text = if (fimDeSemana) "Atendemos no fim de semana!" else "Não atendemos no fim de semana",
                    colorText = colorResource(R.color.text_fim_de_semana)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ButtonsComponent(
                modifier = Modifier,
                buttonwidth = 250.dp,
                buttonOffsetX = 80.dp,
                onClick = { /*  */ },
                buttonOffsetY = 0.dp,
                text = if (telefone == "—") "Sem contato" else "Entrar em contato",
                singleLine = true
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

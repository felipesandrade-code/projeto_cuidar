package br.com.fiap.projetocuidar.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.*
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import coil.compose.AsyncImage
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
    val professionalPhotos = listOf(
        "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?q=80&w=800&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1547038570-cb419a4e030a?q=80&w=800&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1531206715517-5c0ba140b2b8?q=80&w=800&auto=format&fit=crop",
        "https://images.pexels.com/photos/35250445/pexels-photo-35250445.jpeg?auto=compress&cs=tinysrgb&w=800",
        "https://images.pexels.com/photos/7100693/pexels-photo-7100693.jpeg?auto=compress&cs=tinysrgb&w=800",
        "https://images.unsplash.com/photo-1524062734623-a26062363660?q=80&w=800&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1459183885447-df88d1f7124f?q=80&w=800&auto=format&fit=crop"
    )

    val local = vm.selectedOrphanage.value
    val place = vm.selectedPlace.value

    val nome = local?.nome ?: place?.name ?: "Local"
    val lat = local?.lat ?: place?.lat ?: 0.0
    val lng = local?.lng ?: place?.lng ?: 0.0
    val categoria = local?.categoria ?: "—"
    val telefone = local?.telefone ?: "—"
    val sobre = local?.sobre ?: ""
    val instrucao = local?.instrucaoVisita ?: ""
    val horario = local?.horarioVisita ?: ""
    val fimDeSemana = local?.fimDeSemana ?: false

    val context = LocalContext.current

    val verdePrimario = colorResource(R.color.cor_registre)
    val verdeSecundario = colorResource(R.color.cor_card_footer)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.cor_column_registre))
            .verticalScroll(rememberScrollState())
    ) {
        SuperiorOngComponent(Modifier, nome, navController)

        // Foto do orfanato
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            val seed = (local?.id ?: local?.nome ?: "default").hashCode()
            val photoUrl = local?.fotoUrl ?: professionalPhotos[2 + (Math.abs(seed) % (professionalPhotos.size - 2))]
            AsyncImage(
                model = photoUrl,
                error = painterResource(R.drawable.orfanato3),
                placeholder = painterResource(R.drawable.orfanato3),
                contentDescription = nome,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nome
        Text(
            text = nome,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            fontSize = 22.sp,
            color = verdePrimario,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Descrição
        if (sobre.isNotBlank()) {
            Text(
                text = sobre,
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                fontSize = 14.sp,
                color = colorResource(R.color.texto_orfanato),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Mapa mini
        Card(
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 16.dp)
        ) {
            val point = LatLng(lat, lng)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(point, 14f)
            }
            val markerState = rememberMarkerState(position = point)
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(state = markerState, title = nome, snippet = categoria)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_ver_mapa)),
            ) {
                Text(
                    text = "Ver rotas no Google Maps",
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.cor_texto_ver_mapa),
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Instruções para visita",
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            fontSize = 18.sp,
            color = verdePrimario,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (instrucao.isNotBlank()) {
            Text(
                text = instrucao,
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                fontSize = 14.sp,
                color = colorResource(R.color.texto_orfanato),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            Text(
                text = "Nenhuma instrução específica fornecida.",
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                fontSize = 14.sp,
                color = colorResource(R.color.texto_orfanato).copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CardComponentOng(
                modifier = Modifier,
                painter = painterResource(R.drawable.icon_clock),
                contentDescription = "Ícone horário",
                tint = colorResource(R.color.icone_horario),
                cardColors = colorResource(R.color.card_horario_orfanato),
                text = if (horario.isBlank()) "Horário não informado" else horario,
                colorText = colorResource(R.color.text_horario_orfanato)
            )
            Spacer(modifier = Modifier.width(24.dp))
            CardComponentOng(
                painter = painterResource(R.drawable.icons_info),
                contentDescription = "Ícone informação",
                tint = colorResource(R.color.icone_info),
                cardColors = colorResource(R.color.card_fim_de_semana),
                text = if (fimDeSemana) "Atendemos fim de semana" else "Não atendemos fim de semana",
                colorText = colorResource(R.color.text_fim_de_semana)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
// Botão entrar em contato
PrimaryButton(
    text = if (telefone == "—") "Sem contato cadastrado" else "Entrar em contato",
    onClick = {
        val id = local?.id
        val name = local?.nome
        if (!id.isNullOrBlank() && !name.isNullOrBlank()) {
            navController.navigate("chat/$id/$name")
        }
    }
)


        Spacer(modifier = Modifier.height(32.dp))
    }
}

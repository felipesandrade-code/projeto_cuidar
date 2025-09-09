package br.com.fiap.projetocuidar.components.cadastroOrfanato

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.*
import br.com.fiap.projetocuidar.data.Orphanage
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroOngComponents(
    navController: NavController,
    vm: OrphanageViewModel
) {
    // states
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var sobre by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var fimDeSemana by remember { mutableStateOf(false) }
    var instrucoes by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    var nomeError by remember { mutableStateOf<String?>(null) }
    var categoriaError by remember { mutableStateOf<String?>(null) }
    var telefoneError by remember { mutableStateOf<String?>(null) }
    var instrucoesError by remember { mutableStateOf<String?>(null) }
    var horarioError by remember { mutableStateOf<String?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }

    val latSel = vm.selectedLat.value
    val lngSel = vm.selectedLng.value

    Box(
        modifier = Modifier
            .background(colorResource(R.color.cor_column_registre))
            .fillMaxSize()
    ) {
        SuperiorCadastroOng(Modifier, stringResource(R.string.text_name), navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, bottom = 100.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            // ===== Localização =====
            TituloComponents(Modifier, "Localização", 25.sp)
            DividerComponent()

            if (latSel == null || lngSel == null) {
                locationError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                    )
                }
                Button(
                    onClick = {
                        locationError = null
                        navController.navigate("map_select")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Escolher localização no mapa")
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = { },
                        label = { Text("Lat: %.5f, Lng: %.5f".format(latSel, lngSel)) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    TextButton(
                        onClick = { navController.navigate("map_select") },
                        modifier = Modifier.clip(CircleShape)
                    ) { Text("Alterar localização") }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ===== Dados =====
            TituloComponents(Modifier, "Dados", 25.sp)
            DividerComponent()

            TextCadastroOng("Nome")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = nome,
                onvalueChange = { nome = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                singleLine = true,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 56.dp,
                isError = nomeError != null
            )
            nomeError?.let { Text("Campo Nome vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 40.dp, top = 4.dp)) }

            Spacer(Modifier.height(10.dp))
            TextCadastroOng("Categoria")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = categoria,
                onvalueChange = { categoria = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                singleLine = true,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 56.dp,
                isError = categoriaError != null
            )
            categoriaError?.let { Text("Campo Categoria vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 40.dp, top = 4.dp)) }

            Spacer(Modifier.height(10.dp))
            TextCadastroOng("Sobre")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = sobre,
                onvalueChange = { sobre = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
                singleLine = false,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 120.dp,
                isError = false
            )

            Spacer(Modifier.height(10.dp))
            TextCadastroOng("Telefone")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = telefone,
                onvalueChange = { telefone = it },
                keyboardType = KeyboardType.Phone,
                capitalization = KeyboardCapitalization.None,
                singleLine = true,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 56.dp,
                isError = telefoneError != null
            )
            telefoneError?.let { Text("Campo Telefone vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 40.dp, top = 4.dp)) }

            Spacer(Modifier.height(10.dp))
            TextCadastroOng("Foto (URL)")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = foto,
                onvalueChange = { foto = it },
                keyboardType = KeyboardType.Uri,
                capitalization = KeyboardCapitalization.None,
                singleLine = true,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 56.dp,
                isError = false
            )

            Spacer(Modifier.height(16.dp))
            TituloComponents(Modifier, "Visitação", 25.sp)
            DividerComponent()

            TextCadastroOng("Instruções")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = instrucoes,
                onvalueChange = { instrucoes = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
                singleLine = false,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 120.dp,
                isError = instrucoesError != null
            )
            instrucoesError?.let { Text("Campo Instruções vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 40.dp, top = 4.dp)) }

            Spacer(Modifier.height(10.dp))
            TextCadastroOng("Horário das visitas")
            CaixaDeEntradaEmail(
                modifier = Modifier,
                value = horario,
                onvalueChange = { horario = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                singleLine = true,
                caixaDeEntradaWidth = 310.dp,
                caixaDeEntradaPaddingStart = 0.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 40.dp,
                caixaDeEntradaOffsetY = 0.dp,
                caixaDeEntradaSize = 56.dp,
                isError = horarioError != null
            )
            horarioError?.let { Text("Campo Horário vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 40.dp, top = 4.dp)) }

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextCadastroOng("Atende fim de semana?")
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = fimDeSemana,
                    onCheckedChange = { fimDeSemana = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.white),
                        checkedTrackColor = colorResource(R.color.true_atende_final_de_semana)
                    )
                )
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    nomeError = if (nome.isBlank()) "err" else null
                    categoriaError = if (categoria.isBlank()) "err" else null
                    telefoneError = if (telefone.isBlank()) "err" else null
                    instrucoesError = if (instrucoes.isBlank()) "err" else null
                    horarioError = if (horario.isBlank()) "err" else null
                    locationError = if (latSel == null || lngSel == null) "Selecione a localização no mapa" else null

                    val allOk = listOf(nomeError, categoriaError, telefoneError, instrucoesError, horarioError, locationError).all { it == null }
                    if (allOk) {
                        vm.add(
                            Orphanage(
                                nome = nome,
                                categoria = categoria,
                                telefone = telefone,
                                sobre = sobre.ifBlank { null },
                                fotoUrl = foto.ifBlank { null },
                                instrucaoVisita = instrucoes,
                                horarioVisita = horario,
                                fimDeSemana = fimDeSemana,
                                lat = latSel!!,
                                lng = lngSel!!
                            )
                        )
                        vm.clearSelected()
                        navController.navigate("mapa") {
                            popUpTo("mapa") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) { Text(stringResource(R.string.text_cadastrar)) }

            Spacer(Modifier.height(24.dp))
        }
    }
}

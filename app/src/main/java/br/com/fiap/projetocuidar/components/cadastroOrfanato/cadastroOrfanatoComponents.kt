package br.com.fiap.projetocuidar.components.cadastroOrfanato

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var sobre by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var fimDeSemana by remember { mutableStateOf(false) }
    var instrucoes by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    var nomeError by remember { mutableStateOf<String?>(null) }
    var telefoneError by remember { mutableStateOf<String?>(null) }
    var instrucoesError by remember { mutableStateOf<String?>(null) }
    var horarioError by remember { mutableStateOf<String?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }

    val latSel = vm.selectedLat.value
    val lngSel = vm.selectedLng.value

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = colorResource(R.color.cor_registre),
        unfocusedBorderColor = Color.LightGray
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.cor_column_registre))
    ) {
        SuperiorCadastroOng(Modifier, stringResource(R.string.text_name), navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp, bottom = 24.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Spacer(Modifier.height(16.dp))

            // Título "Dados do orfanato"
            TituloComponents(Modifier, "Dados do orfanato", 25.sp)
            DividerComponent()
            Spacer(Modifier.height(16.dp))

            // Localização
            if (latSel == null || lngSel == null) {
                locationError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 20.dp, top = 4.dp, bottom = 4.dp)
                    )
                }
                Button(
                    onClick = { locationError = null; navController.navigate("map_select") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_card_footer))
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
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    TextButton(
                        onClick = { navController.navigate("map_select") },
                        modifier = Modifier.clip(CircleShape)
                    ) { Text("Alterar") }
                }
            }

            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Nome")
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o nome do orfanato", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = nomeError != null
            )
            nomeError?.let { Text("Campo Nome vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp)) }
            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Endereço")
            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o endereço do orfanato", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Sobre")
            OutlinedTextField(
                value = sobre,
                onValueChange = { sobre = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(120.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Telefone")
            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o telefone do orfanato com o DDD", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = telefoneError != null
            )
            telefoneError?.let { Text("Campo Telefone vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp)) }
            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Foto (url)")
            OutlinedTextField(
                value = foto,
                onValueChange = { foto = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )

            Spacer(Modifier.height(20.dp))

            // Seção Visitação
            TituloComponents(Modifier, "Visitação", 25.sp)
            DividerComponent()
            Spacer(Modifier.height(16.dp))

            FormFieldLabel("Instruções")
            OutlinedTextField(
                value = instrucoes,
                onValueChange = { instrucoes = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(120.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = instrucoesError != null
            )
            instrucoesError?.let { Text("Campo Instruções vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp)) }
            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Horário das visitas")
            OutlinedTextField(
                value = horario,
                onValueChange = { horario = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite os horários disponíveis para visitas", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = horarioError != null
            )
            horarioError?.let { Text("Campo Horário vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp)) }
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FormFieldLabel("Atende fim de semana?")
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

            PrimaryButton(
                text = "Cadastrar",
                onClick = {
                    nomeError = if (nome.isBlank()) "err" else null
                    telefoneError = if (telefone.isBlank()) "err" else null
                    instrucoesError = if (instrucoes.isBlank()) "err" else null
                    horarioError = if (horario.isBlank()) "err" else null
                    locationError = if (latSel == null || lngSel == null) "Selecione a localização no mapa" else null

                    val allOk = listOf(nomeError, telefoneError, instrucoesError, horarioError, locationError).all { it == null }
                    if (allOk) {
                        vm.add(
                            Orphanage(
                                nome = nome,
                                categoria = endereco.ifBlank { "Geral" },
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
                }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

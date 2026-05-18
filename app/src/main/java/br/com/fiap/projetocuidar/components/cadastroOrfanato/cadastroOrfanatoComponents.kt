package br.com.fiap.projetocuidar.components.cadastroOrfanato

import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.*
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.Orphanage
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.util.TelefoneVisualTransformation
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogCustom(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    val state = rememberTimePickerState()
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(state.hour, state.minute) }) { Text("Confirmar") }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) { Text("Cancelar") }
        },
        text = {
            TimePicker(state = state)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroOngComponents(
    navController: NavController,
    vm: OrphanageViewModel,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val verdePrimario = colorResource(R.color.cor_registre)
    val context = LocalContext.current
    val geocoder = remember { Geocoder(context) }
    val scope = rememberCoroutineScope()
    var isNavigating by remember { mutableStateOf(false) }

    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var categoriaExpanded by remember { mutableStateOf(false) }
    val categoriasLista = listOf(
        "Acolhimento Infantil",
        "Centro de Reabilitação",
        "Apoio ao Adolescente",
        "Lar de Idosos",
        "Educação Especial",
        "Outros"
    )

    var endereco by remember { mutableStateOf("") }
    var isSearchingLocation by remember { mutableStateOf(false) }
    var sobre by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var fimDeSemana by remember { mutableStateOf(false) }
    var instrucoes by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    var horaAbertura by remember { mutableStateOf("08:00") }
    var horaFechamento by remember { mutableStateOf("18:00") }
    var showAberturaPicker by remember { mutableStateOf(false) }
    var showFechamentoPicker by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { foto = it.toString() }
    }

    LaunchedEffect(horaAbertura, horaFechamento) {
        horario = "$horaAbertura às $horaFechamento"
    }

    val latSel by vm.selectedLat
    val lngSel by vm.selectedLng
    val currentUserId = currentUser?.id

    LaunchedEffect(currentUser, vm.orphanages) {
        val myOrphanage = vm.orphanages.find { it.createdBy == currentUser?.id }
        myOrphanage?.let {
            if (nome.isBlank()) nome = it.nome
            if (categoria.isBlank()) categoria = it.categoria
            if (sobre.isBlank()) sobre = it.sobre ?: ""
            if (telefone.isBlank()) telefone = it.telefone
            if (foto.isBlank()) foto = it.fotoUrl ?: ""
            if (instrucoes.isBlank()) instrucoes = it.instrucaoVisita ?: ""
            
            if (horario.isBlank() || horario == "08:00 às 18:00") {
                it.horarioVisita?.let { h ->
                    horario = h
                    val parts = h.split(" às ")
                    if (parts.size == 2) {
                        horaAbertura = parts[0]
                        horaFechamento = parts[1]
                    }
                }
            }
            
            fimDeSemana = it.fimDeSemana
            
            if (vm.selectedLat.value == null) {
                vm.setSelected(it.lat, it.lng)
            }
        } ?: run {
            currentUser?.let {
                if (nome.isBlank()) nome = it.nome
                if (telefone.isBlank()) telefone = it.telefone
            }
        }
    }

    var nomeError by remember { mutableStateOf<String?>(null) }
    var telefoneError by remember { mutableStateOf<String?>(null) }
    var instrucoesError by remember { mutableStateOf<String?>(null) }
    var horarioError by remember { mutableStateOf<String?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }

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

            TituloComponents(Modifier, "Dados do orfanato", 25.sp)
            DividerComponent()
            Spacer(Modifier.height(16.dp))

            FormFieldLabel("Localização (Digite o endereço)")
            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Ex: Av. Paulista, 1000, São Paulo", color = Color.Gray, fontSize = 13.sp) },
                trailingIcon = {
                    if (isSearchingLocation) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        IconButton(onClick = {
                            if (endereco.isNotBlank()) {
                                isSearchingLocation = true
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        val results = geocoder.getFromLocationName(endereco, 1)
                                        withContext(Dispatchers.Main) {
                                            if (!results.isNullOrEmpty()) {
                                                val loc = results[0]
                                                vm.setSelected(loc.latitude, loc.longitude)
                                                locationError = null
                                            } else {
                                                locationError = "Endereço não encontrado."
                                            }
                                            isSearchingLocation = false
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            locationError = "Erro na busca. Verifique a conexão."
                                            isSearchingLocation = false
                                        }
                                    }
                                }
                            }
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Buscar coordenadas", tint = verdePrimario)
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            
            Spacer(Modifier.height(8.dp))

            if (latSel == null || lngSel == null) {
                locationError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 20.dp, bottom = 4.dp)
                    )
                }
                Button(
                    onClick = { 
                        if (!isNavigating) {
                            isNavigating = true
                            locationError = null
                            navController.navigate("map_select")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_card_footer))
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Ou escolher no mapa manual")
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = { },
                        label = { Text("Local definido: %.4f, %.4f".format(latSel, lngSel)) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = verdePrimario) },
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    TextButton(onClick = { navController.navigate("map_select") }) { Text("Alterar") }
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

            FormFieldLabel("Categoria")
            ExposedDropdownMenuBox(
                expanded = categoriaExpanded,
                onExpandedChange = { categoriaExpanded = !categoriaExpanded },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Selecione a categoria", color = Color.Gray, fontSize = 14.sp) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                ExposedDropdownMenu(
                    expanded = categoriaExpanded,
                    onDismissRequest = { categoriaExpanded = false }
                ) {
                    categoriasLista.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                            onClick = {
                                categoria = item
                                categoriaExpanded = false
                            }
                        )
                    }
                }
            }
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
                onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 11) telefone = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("Digite o telefone do orfanato com o DDD", color = Color.Gray, fontSize = 14.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors,
                isError = telefoneError != null,
                visualTransformation = TelefoneVisualTransformation()
            )
            telefoneError?.let { Text("Campo Telefone vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 24.dp, top = 2.dp)) }
            Spacer(Modifier.height(12.dp))

            FormFieldLabel("Foto da Instituição")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .clickable { galleryLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (foto.isNotBlank()) {
                    AsyncImage(
                        model = foto,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null, tint = Color.Gray)
                        Text("Toque para selecionar foto", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

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
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showAberturaPicker = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = verdePrimario)
                ) {
                    Text("Abre: $horaAbertura")
                }
                OutlinedButton(
                    onClick = { showFechamentoPicker = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = verdePrimario)
                ) {
                    Text("Fecha: $horaFechamento")
                }
            }

            if (showAberturaPicker) {
                TimePickerDialogCustom(
                    onDismiss = { showAberturaPicker = false },
                    onConfirm = { h, m ->
                        horaAbertura = "%02d:%02d".format(h, m)
                        showAberturaPicker = false
                    }
                )
            }
            if (showFechamentoPicker) {
                TimePickerDialogCustom(
                    onDismiss = { showFechamentoPicker = false },
                    onConfirm = { h, m ->
                        horaFechamento = "%02d:%02d".format(h, m)
                        showFechamentoPicker = false
                    }
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
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

            val myOrphanage = remember(vm.orphanages, currentUser) {
                vm.orphanages.find { it.createdBy == currentUser?.id }
            }

            PrimaryButton(
                text = if (myOrphanage != null) "Atualizar" else "Cadastrar",
                onClick = {
                    nomeError = if (nome.isBlank()) "err" else null
                    telefoneError = if (telefone.isBlank()) "err" else null
                    instrucoesError = if (instrucoes.isBlank()) "err" else null
                    horarioError = if (horario.isBlank()) "err" else null
                    locationError = if (latSel == null || lngSel == null) "Selecione" else null

                    val allOk = listOf(nomeError, telefoneError, instrucoesError, horarioError, locationError).all { it == null }
                    if (allOk) {
                        val orphanageData = Orphanage(
                            id = myOrphanage?.id ?: java.util.UUID.randomUUID().toString(),
                            nome = nome,
                            categoria = categoria.ifBlank { "Geral" },
                            telefone = telefone,
                            sobre = sobre.ifBlank { null },
                            fotoUrl = foto.ifBlank { null },
                            instrucaoVisita = instrucoes,
                            horarioVisita = horario,
                            fimDeSemana = fimDeSemana,
                            lat = latSel!!,
                            lng = lngSel!!,
                            createdBy = currentUserId
                        )
                        
                        if (myOrphanage != null) vm.delete(myOrphanage.id)
                        vm.add(orphanageData)
                        vm.clearSelected()
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

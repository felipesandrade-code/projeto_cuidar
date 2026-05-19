package br.com.fiap.projetocuidar.components.cadastroVoluntario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.DividerComponent
import br.com.fiap.projetocuidar.components.FormFieldLabel
import br.com.fiap.projetocuidar.components.PrimaryButton
import br.com.fiap.projetocuidar.components.SuperiorRegister
import br.com.fiap.projetocuidar.components.TituloComponents
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroVoluntarioComponents(
    navController: NavController,
    orphanageViewModel: OrphanageViewModel
) {
    val orphanages = orphanageViewModel.orphanages
    val areasDeAjuda = listOf("Educação", "Saúde", "Artes", "Esportes", "Alimentação", "Apoio psicológico", "Outros")
    val horarios = listOf("Manhã (08h–12h)", "Tarde (13h–17h)", "Noite (18h–22h)", "Fim de semana")

    LaunchedEffect(Unit) {
        orphanageViewModel.loadOrphanages()
    }

    var areaAjuda by remember { mutableStateOf("") }
    var sobreVoce by remember { mutableStateOf("") }
    var orfanatoSelecionado by remember { mutableStateOf("") }
    var horarioSelecionado by remember { mutableStateOf("") }

    var areaExpanded by remember { mutableStateOf(false) }
    var orfanatoExpanded by remember { mutableStateOf(false) }
    var horarioExpanded by remember { mutableStateOf(false) }

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
            .statusBarsPadding()
    ) {
        SuperiorRegister(Modifier, "Cadastro", navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp, bottom = 24.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TituloComponents(Modifier, "Dados do voluntário", 25.sp)
            DividerComponent()
            Spacer(modifier = Modifier.height(16.dp))

            FormFieldLabel("Área de ajuda")
            ExposedDropdownMenuBox(
                expanded = areaExpanded,
                onExpandedChange = { areaExpanded = !areaExpanded },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = areaAjuda,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = areaExpanded) },
                    placeholder = { Text("Selecione a sua área de ajuda", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                ExposedDropdownMenu(
                    expanded = areaExpanded,
                    onDismissRequest = { areaExpanded = false }
                ) {
                    areasDeAjuda.forEach { area ->
                        DropdownMenuItem(
                            text = { Text(area) },
                            onClick = { areaAjuda = area; areaExpanded = false }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Sobre você")
            OutlinedTextField(
                value = sobreVoce,
                onValueChange = { sobreVoce = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(140.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Orfanatos")
            ExposedDropdownMenuBox(
                expanded = orfanatoExpanded,
                onExpandedChange = { orfanatoExpanded = !orfanatoExpanded },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = orfanatoSelecionado,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = orfanatoExpanded) },
                    placeholder = { Text("Selecione um orfanato para ajudar", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                ExposedDropdownMenu(
                    expanded = orfanatoExpanded,
                    onDismissRequest = { orfanatoExpanded = false }
                ) {
                    orphanages.forEach { o ->
                        DropdownMenuItem(
                            text = { Text(o.nome) },
                            onClick = { orfanatoSelecionado = o.nome; orfanatoExpanded = false }
                        )
                    }
                    if (orphanages.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Nenhum orfanato cadastrado") },
                            onClick = { orfanatoExpanded = false }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Horário disponível")
            ExposedDropdownMenuBox(
                expanded = horarioExpanded,
                onExpandedChange = { horarioExpanded = !horarioExpanded },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = horarioSelecionado,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = horarioExpanded) },
                    placeholder = { Text("Selecione um horário para ajudar", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                ExposedDropdownMenu(
                    expanded = horarioExpanded,
                    onDismissRequest = { horarioExpanded = false }
                ) {
                    horarios.forEach { h ->
                        DropdownMenuItem(
                            text = { Text(h) },
                            onClick = { horarioSelecionado = h; horarioExpanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Finalizar Cadastro",
                onClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

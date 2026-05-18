package br.com.fiap.projetocuidar.components.inscricao

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.FormFieldLabel
import br.com.fiap.projetocuidar.components.PrimaryButton
import br.com.fiap.projetocuidar.components.SuperiorComLogo
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel
import br.com.fiap.projetocuidar.data.Volunteer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscricaoComponents(
    navController: NavController,
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    val trabalhos = listOf("Educação", "Saúde", "Artes", "Esportes", "Alimentação", "Apoio psicológico", "Outros")
    
    // Estados para seleção
    var selectedOrphanage by remember { mutableStateOf<br.com.fiap.projetocuidar.data.Orphanage?>(null) }
    var orfanatoSelecionado by remember { mutableStateOf("") }
    var orfanatoIdSelecionado by remember { mutableStateOf("") }
    var trabalhoSelecionado by remember { mutableStateOf("") }
    var horarioSelecionado by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }

    // Gerar lista de horários dinâmica
    val horariosDisponiveis = remember(selectedOrphanage) {
        val base = mutableListOf("Manhã (08h–12h)", "Tarde (13h–17h)", "Noite (18h–22h)")
        if (selectedOrphanage?.fimDeSemana == true) {
            base.add("Fim de semana")
        }
        // Se o orfanato tiver um horário específico cadastrado, adicionamos como opção
        selectedOrphanage?.horarioVisita?.let { if (it.isNotBlank()) base.add(0, "Horário da Instituição: $it") }
        base
    }

    var orfanatoExpanded by remember { mutableStateOf(false) }
    var trabalhoExpanded by remember { mutableStateOf(false) }
    var horarioExpanded by remember { mutableStateOf(false) }
    var saved by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = colorResource(R.color.cor_registre),
        unfocusedBorderColor = Color.LightGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.cor_column_registre))
            .verticalScroll(rememberScrollState())
    ) {
        SuperiorComLogo(navcontroller = navController)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Inscrição voluntariado",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            color = colorResource(R.color.cor_registre),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        FormFieldLabel("Orfanato/instituição")
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
                placeholder = { Text("Selecione o orfanato", color = Color.Gray, fontSize = 14.sp) },
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
                        text = { Text(o.nome, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                        onClick = { 
                            selectedOrphanage = o
                            orfanatoSelecionado = o.nome
                            orfanatoIdSelecionado = o.id
                            horarioSelecionado = "" // Resetar horário ao trocar de ONG
                            orfanatoExpanded = false 
                        }
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

        FormFieldLabel("Trabalhos disponíveis")
        ExposedDropdownMenuBox(
            expanded = trabalhoExpanded,
            onExpandedChange = { trabalhoExpanded = !trabalhoExpanded },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = trabalhoSelecionado,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = trabalhoExpanded) },
                placeholder = { Text("Selecione o trabalho", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            ExposedDropdownMenu(
                expanded = trabalhoExpanded,
                onDismissRequest = { trabalhoExpanded = false }
            ) {
                trabalhos.forEach { t ->
                    DropdownMenuItem(
                        text = { Text(t, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                        onClick = { trabalhoSelecionado = t; trabalhoExpanded = false }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("Horários disponíveis")
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
                placeholder = { Text("Selecione  um horário", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            ExposedDropdownMenu(
                expanded = horarioExpanded,
                onDismissRequest = { horarioExpanded = false }
            ) {
                horariosDisponiveis.forEach { h ->
                    DropdownMenuItem(
                        text = { Text(h, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                        onClick = { horarioSelecionado = h; horarioExpanded = false }
                    )
                }
            }        }
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("Observações sobre a sua ajuda")
        OutlinedTextField(
            value = observacoes,
            onValueChange = { if (it.length <= 255) observacoes = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(140.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
            singleLine = false,
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Text(
            text = "${observacoes.length}/255 caracteres",
            fontSize = 11.sp,
            color = colorResource(R.color.cor_text_login),
            modifier = Modifier.padding(end = 24.dp).align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (saved) {
            Text(
                text = "Inscrição salva com sucesso!",
                color = colorResource(R.color.cor_registre),
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
            )
        }

        PrimaryButton(
            text = "Salvar inscrição",
            onClick = { 
                val volunteer = Volunteer(
                    userId = currentUser?.id, // ID real do usuário para mensagens
                    orphanageId = orfanatoIdSelecionado,
                    name = "${currentUser?.nome ?: "Voluntário"} ${currentUser?.sobrenome ?: ""}".trim(),
                    email = currentUser?.email ?: "",
                    task = trabalhoSelecionado,
                    isAvailable = true
                )
                managementViewModel.addVolunteer(volunteer)
                saved = true 
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

package br.com.fiap.projetocuidar.components.doacao

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import br.com.fiap.projetocuidar.components.navigation.AppBottomBar
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.Donation
import br.com.fiap.projetocuidar.data.ManagementViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoacaoComponents(
    navController: NavController,
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    val context = LocalContext.current
    val tiposDoacao = listOf("Dinheiro", "Roupas", "Alimentos", "Brinquedos", "Materiais escolares", "Outros")

    var orfanatoSelecionado by remember { mutableStateOf("") }
    var orfanatoIdSelecionado by remember { mutableStateOf("") }
    var tipoDoacao by remember { mutableStateOf("") }
    var valorDoacao by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    var formaDePagamento by remember { mutableStateOf("") }
    var pixCopiado by remember { mutableStateOf(false) }

    var orfanatoExpanded by remember { mutableStateOf(false) }
    var tipoExpanded by remember { mutableStateOf(false) }
    var saved by remember { mutableStateOf(false) }

    val verdePrimario = colorResource(R.color.cor_registre)

    LaunchedEffect(tipoDoacao) {
        if (tipoDoacao != "Dinheiro") {
            formaDePagamento = ""
            pixCopiado = false
        }
    }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = verdePrimario,
        unfocusedBorderColor = Color.LightGray
    )

    Scaffold(
        bottomBar = {
            AppBottomBar(
                navController = navController,
                authViewModel = authViewModel,
                currentRoute = "doacao"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            SuperiorComLogo(navcontroller = navController)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Doação",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                color = verdePrimario,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
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
                ExposedDropdownMenu(expanded = orfanatoExpanded, onDismissRequest = { orfanatoExpanded = false }) {
                    orphanages.forEach { o ->
                        DropdownMenuItem(
                            text = { Text(o.nome, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                            onClick = {
                                orfanatoSelecionado = o.nome
                                orfanatoIdSelecionado = o.id
                                orfanatoExpanded = false
                            }
                        )
                    }
                    if (orphanages.isEmpty()) {
                        DropdownMenuItem(text = { Text("Nenhum orfanato cadastrado") }, onClick = { orfanatoExpanded = false })
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Tipo da doação")
            ExposedDropdownMenuBox(
                expanded = tipoExpanded,
                onExpandedChange = { tipoExpanded = !tipoExpanded },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = tipoDoacao,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpanded) },
                    placeholder = { Text("Selecione o tipo da doação", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                ExposedDropdownMenu(expanded = tipoExpanded, onDismissRequest = { tipoExpanded = false }) {
                    tiposDoacao.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo, fontFamily = FontFamily(Font(R.font.nunito_regular))) },
                            onClick = { tipoDoacao = tipo; tipoExpanded = false }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            if (tipoDoacao == "Dinheiro") {
                val orfanatoObj = orphanages.find { it.id == orfanatoIdSelecionado }

                FormFieldLabel("Valor da doação")
                OutlinedTextField(
                    value = valorDoacao,
                    onValueChange = { valorDoacao = it },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    placeholder = { Text("R$ 0,00", color = Color.Gray, fontSize = 14.sp) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors,
                    prefix = { Text("R$ ", color = Color.Gray) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                FormFieldLabel("Forma de pagamento")
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val pixSelecionado = formaDePagamento == "PIX"
                    OutlinedButton(
                        onClick = { formaDePagamento = "PIX"; pixCopiado = false },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = if (pixSelecionado)
                            ButtonDefaults.buttonColors(containerColor = verdePrimario, contentColor = Color.White)
                        else
                            ButtonDefaults.outlinedButtonColors(contentColor = verdePrimario),
                        border = BorderStroke(1.5.dp, verdePrimario)
                    ) {
                        Text("PIX", fontFamily = FontFamily(Font(R.font.nunito_bold)), fontSize = 15.sp)
                    }

                    val localSelecionado = formaDePagamento == "Local"
                    OutlinedButton(
                        onClick = { formaDePagamento = "Local" },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = if (localSelecionado)
                            ButtonDefaults.buttonColors(containerColor = verdePrimario, contentColor = Color.White)
                        else
                            ButtonDefaults.outlinedButtonColors(contentColor = verdePrimario),
                        border = BorderStroke(1.5.dp, verdePrimario)
                    ) {
                        Text("No Local", fontFamily = FontFamily(Font(R.font.nunito_bold)), fontSize = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (formaDePagamento == "PIX") {
                    val chavePix = orfanatoObj?.telefone ?: ""
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(0.dp),
                        border = BorderStroke(1.dp, verdePrimario.copy(alpha = 0.25f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Chave PIX da Instituição",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                fontFamily = FontFamily(Font(R.font.nunito_regular))
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            if (chavePix.isNotBlank()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = chavePix,
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                                        color = verdePrimario
                                    )
                                    IconButton(onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Chave PIX", chavePix))
                                        pixCopiado = true
                                    }) {
                                        Icon(
                                            imageVector = if (pixCopiado) Icons.Default.CheckCircle else Icons.Default.ContentCopy,
                                            contentDescription = if (pixCopiado) "Copiado" else "Copiar chave PIX",
                                            tint = if (pixCopiado) Color(0xFF4CAF50) else verdePrimario
                                        )
                                    }
                                }

                                if (pixCopiado) {
                                    Text(
                                        "Chave copiada! Abra seu app de banco e cole.",
                                        fontSize = 12.sp,
                                        color = Color(0xFF4CAF50),
                                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    color = Color.LightGray.copy(alpha = 0.5f)
                                )
                                Text(
                                    "Tipo: Telefone  •  Após pagar, informe o valor acima e salve a doação.",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    fontFamily = FontFamily(Font(R.font.nunito_regular))
                                )
                            } else {
                                Text(
                                    "Este orfanato ainda não configurou uma chave PIX.",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "Entre em contato via chat para combinar o pagamento.",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (formaDePagamento == "Local") {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(0.dp),
                        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = verdePrimario,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "Pagamento presencial",
                                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                                    fontSize = 14.sp,
                                    color = verdePrimario
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Você entregará o valor diretamente ao orfanato. Combine um horário pelo chat ou telefone antes de ir.",
                                fontSize = 13.sp,
                                color = Color.Gray,
                                fontFamily = FontFamily(Font(R.font.nunito_regular))
                            )
                            val telefone = orfanatoObj?.telefone
                            if (!telefone.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Contato: $telefone",
                                    fontSize = 13.sp,
                                    color = verdePrimario,
                                    fontFamily = FontFamily(Font(R.font.nunito_bold))
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            FormFieldLabel("Mensagem de apoio")
            OutlinedTextField(
                value = mensagem,
                onValueChange = { if (it.length <= 255) mensagem = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(140.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )
            Text(
                text = "${mensagem.length}/255",
                fontSize = 11.sp,
                color = colorResource(R.color.cor_text_login),
                modifier = Modifier.padding(end = 24.dp).align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (saved) {
                Text(
                    text = "Doação registrada com sucesso!",
                    color = verdePrimario,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
                )
            }

            val labelBotao = when {
                tipoDoacao == "Dinheiro" && formaDePagamento == "PIX" -> "Confirmar doação via PIX"
                tipoDoacao == "Dinheiro" && formaDePagamento == "Local" -> "Confirmar doação no local"
                else -> "Salvar doação"
            }

            PrimaryButton(
                text = labelBotao,
                onClick = {
                    val pagamento = when (formaDePagamento) {
                        "PIX" -> " (via PIX)"
                        "Local" -> " (no local)"
                        else -> ""
                    }
                    val donation = Donation(
                        orphanageId = orfanatoIdSelecionado,
                        donorName = "${currentUser?.nome ?: "Doador"} ${currentUser?.sobrenome ?: ""}".trim(),
                        type = "$tipoDoacao$pagamento",
                        value = if (valorDoacao.isNotBlank()) "R$ $valorDoacao" else null,
                        message = mensagem
                    )
                    managementViewModel.addDonation(donation)
                    saved = true
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

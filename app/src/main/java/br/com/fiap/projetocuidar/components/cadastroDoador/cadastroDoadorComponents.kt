package br.com.fiap.projetocuidar.components.cadastroDoador

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroDoadorComponents(navController: NavController) {
    val tiposDoacao = listOf("Dinheiro", "Roupas", "Alimentos", "Brinquedos", "Materiais escolares", "Outros")

    var tipoDoacao by remember { mutableStateOf("") }
    var sobre by remember { mutableStateOf("") }
    var tipoExpanded by remember { mutableStateOf(false) }

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

            TituloComponents(Modifier, "Dados do doador", 25.sp)
            DividerComponent()
            Spacer(modifier = Modifier.height(16.dp))

            FormFieldLabel("Tipo de doação")
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
                    placeholder = { Text("Selecione um tipo de doação", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                ExposedDropdownMenu(
                    expanded = tipoExpanded,
                    onDismissRequest = { tipoExpanded = false }
                ) {
                    tiposDoacao.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = { tipoDoacao = tipo; tipoExpanded = false }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            FormFieldLabel("Sobre você ou sobre a organização")
            OutlinedTextField(
                value = sobre,
                onValueChange = { sobre = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(160.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )

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

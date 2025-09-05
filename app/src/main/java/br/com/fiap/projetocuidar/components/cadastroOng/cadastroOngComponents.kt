package br.com.fiap.projetocuidar.components.cadastroOng

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.ButtonsComponent
import br.com.fiap.projetocuidar.components.CaixaDeEntradaComponent
import br.com.fiap.projetocuidar.components.DividerComponent
import br.com.fiap.projetocuidar.components.SuperiorCadastroOng
import br.com.fiap.projetocuidar.components.TextCadastroOng
import br.com.fiap.projetocuidar.components.TituloComponents


@Composable
fun CadastroOngComponents(navController: NavController.Companion) {
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

    Box(
        modifier = Modifier //Box de toda a aplicação
            .background(colorResource(R.color.cor_column_registre))
            .fillMaxSize()
    ) {
        SuperiorCadastroOng()
        //Formulário com dados importantes sobre o orfanato
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 80.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            TituloComponents(Modifier, "Dados", 25.sp)
            DividerComponent()
            TextCadastroOng(stringResource(R.string.text_name))
            CaixaDeEntradaComponent(
                Modifier,
                value = nome,
                onvalueChange = { nome = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
                singleLine = false,
                isError =  nomeError != null
            )
            nomeError?.let { Text("Campo Nome vazio, por favor digite um nome", color = Color.Red, fontSize = 14.sp) }
            Spacer(modifier = Modifier.height(30.dp))
            TextCadastroOng(stringResource(R.string.text_categoria))
            CaixaDeEntradaComponent(
                Modifier,
                value = categoria,
                onvalueChange = { categoria = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
                singleLine = false,
                isError = categoriaError != null
            )
            categoriaError?.let { Text("Campo categoria vazio, por favor digite uma categoria", color = Color.Red, fontSize = 14.sp) }

            Spacer(modifier = Modifier.height(35.dp))

            TextCadastroOng("Sobre")
            CaixaDeEntradaComponent(
                Modifier,
                value = sobre,
                onvalueChange = { sobre = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 2.dp,
                caixaDeEntradaSize = 70.dp,
                singleLine = false,
                isError = false
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextCadastroOng("Telefone")
            CaixaDeEntradaComponent(
                Modifier,
                value = telefone,
                onvalueChange = { telefone = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
                singleLine = false,
                isError = telefoneError != null
            )
            telefoneError?.let { Text("Campo telefone vazio, por favor digite um telefone", color = Color.Red, fontSize = 14.sp) }

            Spacer(modifier = Modifier.height(35.dp))

            TextCadastroOng("Foto (url)")
            CaixaDeEntradaComponent(
                Modifier,
                value = foto,
                onvalueChange = { foto = it },
                keyboardType = KeyboardType.Uri,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
                false,
                isError = false
            )


            Spacer(modifier = Modifier.height(30.dp))

            //Intruções e horário sobre a Visitação do orfanato.
            TituloComponents(modifier = Modifier, text = "Visitação", fontSize = 25.sp)
            DividerComponent()
            Spacer(modifier = Modifier.height(10.dp))
            TextCadastroOng("Instruções")
            CaixaDeEntradaComponent(
                Modifier,
                value = instrucoes,
                onvalueChange = { instrucoes = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 2.dp,
                caixaDeEntradaSize = 70.dp,
                singleLine = false,
                isError = instrucoesError != null
            )
            instrucoesError?.let { Text("Campo de instruções vazio, por favor digite alguma instrução.", color = Color.Red, fontSize = 14.sp) }

            Spacer(modifier = Modifier.height(15.dp))

            TextCadastroOng("Horário das visitas")
            CaixaDeEntradaComponent(
                Modifier,
                value = horario,
                onvalueChange = { horario = it },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
                singleLine = false,
                isError = horarioError != null
            )
            horarioError?.let { Text("Campo de horário vazio, por favor digite um horário.", color = Color.Red, fontSize = 14.sp) }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 15.dp)
            ) {
                TextCadastroOng("Atende fim de semana?")
                Spacer(modifier = Modifier.width(80.dp))
                Switch(
                    checked = fimDeSemana,
                    onCheckedChange = { fimDeSemana = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.white),
                        checkedTrackColor = colorResource(R.color.true_atende_final_de_semana)
                    ),
                    modifier = Modifier
                        .scale(0.7f)
                        .offset(y = -18.dp),
                )
            }
            ButtonsComponent(
                Modifier,
                buttonwidth = 200.dp,
                buttonOffsetX = 100.dp,
                buttonOffsetY = 10.dp,
                onClick = {
                    // validação de campos
                    nomeError = if (nome.isBlank()) "Digite o nome" else null
                    categoriaError = if (categoria.isBlank()) "Digite a categoria" else null
                    telefoneError = if (telefone.isBlank()) "Digite o telefone" else null
                    instrucoesError = if (instrucoes.isBlank()) "Digite as instruções" else null
                    horarioError = if (horario.isBlank()) "Digite o horário" else null

                    // se todos válidos
                    if (listOf(
                            nomeError, categoriaError, telefoneError, instrucoesError, horarioError
                        ).all { it == null }) {
                        navController.navigate("home")
                    }
                },
                text = stringResource(R.string.text_cadastrar),
                singleLine = true
            )
        }
    }
}

private fun NavController.Companion.navigate(string: String) {}

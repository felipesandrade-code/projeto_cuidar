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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.projetocuidar.R


@Composable
fun CadastroOngComponents(modifier: Modifier = Modifier) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var sobre by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var fimDeSemana by remember { mutableStateOf(false) }
    var instrucoes by remember { mutableStateOf("") }

    Box(modifier = Modifier //Box de toda a aplicação
        .background(colorResource(R.color.cor_column_registre))
        .fillMaxSize()) {
        SuperiorCadastroOng()
        //Formulário com dados importantes sobre o orfanato
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 80.dp)
        ) {
            TituloComponents(Modifier, "Dados", 25.sp)
            DividerComponent()
            TextCadastroOng(stringResource(R.string.text_name))
            CaixasDeEntradaTextComponents(
                value = nome,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
            TextCadastroOng(stringResource(R.string.text_categoria))
            CaixasDeEntradaTextComponents(
                value = categoria,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
            TextCadastroOng("Sobre")
            CaixaDeEntradaMaior(
                modifier = Modifier,
                value = sobre,
                onvalueChange = { "" },
            )
            Text(
                text = "Telefone",
                fontSize = 15.sp,
                color = colorResource(R.color.cor_text_login),
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                modifier = Modifier.padding(start = 32.dp, bottom = 2.dp)
            )
            CaixasDeEntradaTextComponents(
                value = telefone,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
            TextCadastroOng("Foto (url)")
            CaixasDeEntradaTextComponents(
                value = foto,
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    capitalization = KeyboardCapitalization.Words
                ),
            )

            //Intruções e horário sobre a Visitação do orfanato.
            TituloComponents(modifier = Modifier, text = "Visitação", fontSize = 25.sp)
            DividerComponent()
            Spacer(modifier = Modifier.height(10.dp))
            TextCadastroOng("Instruções")
            CaixaDeEntradaMaior(
                modifier = Modifier,
                value = instrucoes,
                onvalueChange = { "" },
            )
            TextCadastroOng("Horário das visitas")
            CaixasDeEntradaTextComponents(
                value = "",
                onvalueChange = { "" },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
            )
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
            buttonCadastroOng(onClick = {})
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CadastroOngComponentsPreview() {
    CadastroOngComponents()
}
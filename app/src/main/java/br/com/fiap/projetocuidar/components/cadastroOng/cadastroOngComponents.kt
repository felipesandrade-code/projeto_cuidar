package br.com.fiap.projetocuidar.components.cadastroOng

import android.widget.Space
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
import br.com.fiap.projetocuidar.components.ButtonsComponent
import br.com.fiap.projetocuidar.components.DividerComponent
import br.com.fiap.projetocuidar.components.TituloComponents
import br.com.fiap.projetocuidar.components.CaixaDeEntradaComponent
import org.w3c.dom.Text


@Composable
fun CadastroOngComponents(modifier: Modifier = Modifier) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var sobre by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var fimDeSemana by remember { mutableStateOf(false) }
    var instrucoes by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(20.dp))
            TituloComponents(Modifier, "Dados", 25.sp)
            DividerComponent()
            TextCadastroOng(stringResource(R.string.text_name))
            CaixaDeEntradaComponent(
                Modifier,
                value = nome,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextCadastroOng(stringResource(R.string.text_categoria))
            CaixaDeEntradaComponent(
                Modifier,
                value = categoria,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
            )
            Spacer(modifier = Modifier.height(35.dp))
            TextCadastroOng("Sobre")
            CaixaDeEntradaComponent(
                Modifier,
                value = sobre,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 2.dp,
                caixaDeEntradaSize = 70.dp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextCadastroOng("Telefone")
            CaixaDeEntradaComponent(
                Modifier,
                value = telefone,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
            )
            Spacer(modifier = Modifier.height(35.dp))
            TextCadastroOng("Foto (url)")
            CaixaDeEntradaComponent(
                    Modifier,
                    value = foto    ,
                    onvalueChange = { "" },
                    keyboardType = KeyboardType.Uri,
                    capitalization = KeyboardCapitalization.Words,
                    caixaDeEntradaWidth = 350.dp,
                    caixaDeEntradaPaddingStart = 10.dp,
                    caixaDeEntradaPaddingTop = 0.dp,
                    caixaDeEntradaOffsetX = 20.dp,
                    caixaDeEntradaOffsetY = 15.dp,
                    caixaDeEntradaSize = 0.dp
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
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 2.dp,
                caixaDeEntradaSize = 70.dp,
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextCadastroOng("Horário das visitas")
            CaixaDeEntradaComponent(
                Modifier,
                value = horario ,
                onvalueChange = { "" },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                caixaDeEntradaWidth = 350.dp,
                caixaDeEntradaPaddingStart = 10.dp,
                caixaDeEntradaPaddingTop = 0.dp,
                caixaDeEntradaOffsetX = 20.dp,
                caixaDeEntradaOffsetY = 15.dp,
                caixaDeEntradaSize = 0.dp,
            )
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
                onClick = {/*TODO*/ },
                text = stringResource(R.string.text_cadastrar),
                buttonwidth = 200.dp,
                buttonOffsetX = 100.dp,
                buttonOffsetY = 10.dp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CadastroOngComponentsPreview() {
    CadastroOngComponents()
}
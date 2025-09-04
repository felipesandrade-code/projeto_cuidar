package br.com.fiap.projetocuidar.components.cadastroOng

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CaixaDeEntradaMaior(
    modifier: Modifier,
    value: String,
    onvalueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {onvalueChange(value)},
        modifier = modifier
            .width(320.dp)
            .size(90.dp)
            .padding(start = 10.dp, top = 2.dp)
            .offset(x = 20.dp),
        keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
        capitalization = KeyboardCapitalization.Words,
        ),
        shape = Shapes().medium
    )
}
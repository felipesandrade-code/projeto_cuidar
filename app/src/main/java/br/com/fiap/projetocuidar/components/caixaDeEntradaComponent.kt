package br.com.fiap.projetocuidar.components

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
import androidx.compose.ui.unit.Dp

@Composable
fun CaixaDeEntradaComponent(
    modifier: Modifier = Modifier,
    value: String,
    onvalueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    capitalization: KeyboardCapitalization,
    caixaDeEntradaWidth: Dp,
    caixaDeEntradaPaddingStart: Dp,
    caixaDeEntradaPaddingTop: Dp,
    caixaDeEntradaOffsetX: Dp,
    caixaDeEntradaOffsetY:  Dp,
    caixaDeEntradaSize: Dp,
    singleLine: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = {onvalueChange(value)},
        modifier = modifier
            .width(caixaDeEntradaWidth)
            .size(caixaDeEntradaSize)
            .padding(caixaDeEntradaPaddingStart,caixaDeEntradaPaddingTop)
            .offset(caixaDeEntradaOffsetX, caixaDeEntradaOffsetY),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = Shapes().medium
    )
}
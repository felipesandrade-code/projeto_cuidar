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
import androidx.compose.ui.unit.dp

@Composable
fun CaixasDeEntradaTextComponents(
    value: String,
    onvalueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
){
    OutlinedTextField(
        value = value,
        onValueChange = { onvalueChange(value) },
        modifier = Modifier
            .width(320.dp)
            .size(40.dp)
            .padding(start = 10.dp, top = 2.dp)
            .offset(x = 20.dp),
        keyboardOptions = keyboardOptions,
        shape = Shapes().medium
    )
}
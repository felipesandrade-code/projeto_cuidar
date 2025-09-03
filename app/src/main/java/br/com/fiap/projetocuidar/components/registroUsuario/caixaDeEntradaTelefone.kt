package br.com.fiap.projetocuidar.components.registroUsuario

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CaixaDeEntradaTelefone(
    modifier: Modifier = Modifier,
    value: String,
    onvalueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    keyboardOptions: KeyboardOptions,
    shape: CornerBasedShape
) {
    OutlinedTextField(
        value = "",
        onValueChange = { "" },
        modifier = Modifier
            .width(320.dp)
            .size(40.dp)
            .padding(10.dp)
            .offset(x = 20.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        shape = Shapes().medium
    )
}
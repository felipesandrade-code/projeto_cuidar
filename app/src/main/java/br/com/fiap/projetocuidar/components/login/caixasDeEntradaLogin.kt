package br.com.fiap.projetocuidar.components.login

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun CaixasDeEntradaLogin(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = { "" },
        shape = Shapes().medium,
        modifier = Modifier
            .offset(x = 40.dp)
            .width(310.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            colorResource(R.color.black),
            unfocusedContainerColor = colorResource(R.color.white),
            unfocusedBorderColor = colorResource(R.color.cor_card_footer),
            disabledContainerColor = colorResource(R.color.white),
            cursorColor = colorResource(R.color.black)
        ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        keyboardActions = KeyboardActions()
    )
}
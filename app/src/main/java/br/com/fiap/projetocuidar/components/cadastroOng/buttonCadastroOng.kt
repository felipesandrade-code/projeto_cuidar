package br.com.fiap.projetocuidar.components.cadastroOng

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.TextButtons


@Composable
fun buttonCadastroOng(
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = Modifier.offset(x = 100.dp),
        shape = Shapes().small,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer))
    ) {
        TextButtons(Modifier, "Cadastrar")
    }
}
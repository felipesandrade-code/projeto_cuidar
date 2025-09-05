package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import br.com.fiap.projetocuidar.R


@Composable
fun ButtonsComponent(
    modifier: Modifier,
    buttonwidth: Dp,
    buttonOffsetX: Dp,
    buttonOffsetY: Dp,
    onClick: () -> Unit,
    text: String,
    singleLine: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(buttonwidth)
            .offset(x = buttonOffsetX, y = buttonOffsetY),
        shape = Shapes().small,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_card_footer)),
    ){
        TextButtons(
            text = text,
            singleLine = singleLine
        )
    }
}
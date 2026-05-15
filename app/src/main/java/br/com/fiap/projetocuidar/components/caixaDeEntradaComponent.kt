package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

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
    singleLine: Boolean,
    isError: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val minheight = if (caixaDeEntradaSize < 56.dp) 56.dp else caixaDeEntradaSize
    OutlinedTextField(
        value = value,
        onValueChange = {onvalueChange(it)},
        modifier = modifier
            .width(caixaDeEntradaWidth)
            .size(caixaDeEntradaSize)
            .padding(caixaDeEntradaPaddingStart, caixaDeEntradaPaddingTop)
            .offset(caixaDeEntradaOffsetX, caixaDeEntradaOffsetY)
            .heightIn(min = minheight),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = Shapes().medium,
        textStyle = TextStyle(color = colorResource(R.color.black)),
        isError = isError,
        visualTransformation = visualTransformation
    )
}

@Composable
fun CaixaDeEntradaSenha(
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
    singleLine: Boolean,
    isPassword: Boolean = false
) {
    var senhaOculta by remember { mutableStateOf(true) }

    val minheight = if (caixaDeEntradaSize < 56.dp) 56.dp else caixaDeEntradaSize
    OutlinedTextField(
        value = value,
        onValueChange = {onvalueChange(it)},
        modifier = modifier
            .width(caixaDeEntradaWidth)
            .size(caixaDeEntradaSize)
            .padding(caixaDeEntradaPaddingStart, caixaDeEntradaPaddingTop)
            .offset(caixaDeEntradaOffsetX, caixaDeEntradaOffsetY)
            .heightIn(min = minheight),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = Shapes().medium,
        textStyle = TextStyle(color = colorResource(R.color.black)),
        visualTransformation = if (isPassword && senhaOculta) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                val imagem = if (senhaOculta) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(onClick = { senhaOculta = !senhaOculta }) {
                    Icon(
                        imageVector = imagem,
                        contentDescription = if (senhaOculta) "Mostrar senha" else "Ocultar senha"
                    )
                }
            }
        }
    )
}

@Composable
fun CaixaDeEntradaEmail(
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
    singleLine: Boolean,
    isError: Boolean
) {
    val minheight = if (caixaDeEntradaSize < 56.dp) 56.dp else caixaDeEntradaSize
    OutlinedTextField(
        value = value,
        onValueChange = {onvalueChange(it)},
        modifier = modifier
            .width(caixaDeEntradaWidth)
            .size(caixaDeEntradaSize)
            .padding(caixaDeEntradaPaddingStart, caixaDeEntradaPaddingTop)
            .offset(caixaDeEntradaOffsetX, caixaDeEntradaOffsetY)
            .heightIn(min = minheight),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = Shapes().medium,
        textStyle = TextStyle(color = colorResource(R.color.black)),
        isError = isError
    )
}
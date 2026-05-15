package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
private fun caixaDeEntradaColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = colorResource(R.color.black),
    unfocusedTextColor = colorResource(R.color.black),
    disabledTextColor = colorResource(R.color.cor_text_login),
    errorTextColor = colorResource(R.color.color_erro_email),
    cursorColor = colorResource(R.color.cor_registre),
    focusedBorderColor = colorResource(R.color.cor_registre),
    unfocusedBorderColor = colorResource(R.color.cor_text_login),
    errorBorderColor = colorResource(R.color.color_erro_email),
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    errorContainerColor = Color.White,
)

private fun Modifier.caixaDeEntradaLayout(
    caixaDeEntradaOffsetX: Dp,
    caixaDeEntradaOffsetY: Dp,
    caixaDeEntradaPaddingStart: Dp,
    caixaDeEntradaPaddingTop: Dp,
    caixaDeEntradaSize: Dp,
): Modifier = this
    .fillMaxWidth()
    .padding(
        start = caixaDeEntradaOffsetX + caixaDeEntradaPaddingStart,
        end = caixaDeEntradaOffsetX,
        top = caixaDeEntradaOffsetY + caixaDeEntradaPaddingTop,
    )
    .heightIn(min = maxOf(caixaDeEntradaSize, 56.dp))

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
    caixaDeEntradaOffsetY: Dp,
    caixaDeEntradaSize: Dp,
    singleLine: Boolean,
    isError: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onvalueChange,
        modifier = modifier.caixaDeEntradaLayout(
            caixaDeEntradaOffsetX,
            caixaDeEntradaOffsetY,
            caixaDeEntradaPaddingStart,
            caixaDeEntradaPaddingTop,
            caixaDeEntradaSize,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = MaterialTheme.shapes.medium,
        colors = caixaDeEntradaColors(),
        textStyle = TextStyle(color = colorResource(R.color.black)),
        isError = isError,
        visualTransformation = visualTransformation,
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
    caixaDeEntradaOffsetY: Dp,
    caixaDeEntradaSize: Dp,
    singleLine: Boolean,
    isPassword: Boolean = false
) {
    var senhaOculta by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = value,
        onValueChange = onvalueChange,
        modifier = modifier.caixaDeEntradaLayout(
            caixaDeEntradaOffsetX,
            caixaDeEntradaOffsetY,
            caixaDeEntradaPaddingStart,
            caixaDeEntradaPaddingTop,
            caixaDeEntradaSize,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = MaterialTheme.shapes.medium,
        colors = caixaDeEntradaColors(),
        textStyle = TextStyle(color = colorResource(R.color.black)),
        visualTransformation = if (isPassword && senhaOculta) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val imagem = if (senhaOculta) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(onClick = { senhaOculta = !senhaOculta }) {
                    Icon(
                        imageVector = imagem,
                        contentDescription = if (senhaOculta) "Mostrar senha" else "Ocultar senha",
                        tint = colorResource(R.color.cor_registre),
                    )
                }
            }
        },
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
    caixaDeEntradaOffsetY: Dp,
    caixaDeEntradaSize: Dp,
    singleLine: Boolean,
    isError: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onvalueChange,
        modifier = modifier.caixaDeEntradaLayout(
            caixaDeEntradaOffsetX,
            caixaDeEntradaOffsetY,
            caixaDeEntradaPaddingStart,
            caixaDeEntradaPaddingTop,
            caixaDeEntradaSize,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
        ),
        singleLine = singleLine,
        shape = MaterialTheme.shapes.medium,
        colors = caixaDeEntradaColors(),
        textStyle = TextStyle(color = colorResource(R.color.black)),
        isError = isError,
    )
}

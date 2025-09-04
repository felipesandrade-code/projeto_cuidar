package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun SwitchComponent(
    modifier: Modifier = Modifier,
    fimDeSemana: Boolean,
    onCheckedChange: (fimDeSemana: Boolean) -> Unit
) {
    Switch(
        checked = fimDeSemana,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = colorResource(R.color.white),
            checkedTrackColor = colorResource(R.color.true_atende_final_de_semana)
        ),
        modifier = Modifier
            .scale(0.7f)
            .offset(y = -18.dp),
    )
}
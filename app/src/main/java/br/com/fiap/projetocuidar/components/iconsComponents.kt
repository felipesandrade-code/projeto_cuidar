package br.com.fiap.projetocuidar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.projetocuidar.R

@Composable
fun IconesLogin(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.icons_facebook),
            contentDescription = "Logo Facebook",
            tint = colorResource(R.color.cor_registre),
            modifier = Modifier
                .offset(y = 70.dp)
                .size(30.dp)
        )
        Spacer(modifier = Modifier.width(35.dp))
        Icon(
            painter = painterResource(R.drawable.icons_google),
            contentDescription = "Logo google",
            tint = colorResource(R.color.cor_registre),
            modifier = Modifier
                .size(30.dp)
                .offset(y = 70.dp)
        )
    }
}
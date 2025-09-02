package br.com.fiap.projetocuidar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.fiap.projetocuidar.navigation.AppNavHost

import br.com.fiap.projetocuidar.ui.theme.ProjetoCuidarTheme
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val playServicesOk =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS
        setContent {
            ProjetoCuidarTheme {
                AppNavHost(playServicesOk = playServicesOk)
            }
        }
    }
}

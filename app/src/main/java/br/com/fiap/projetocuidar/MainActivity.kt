package br.com.fiap.projetocuidar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.fiap.projetocuidar.navigation.AppNavHost
import br.com.fiap.projetocuidar.ui.theme.ProjetoCuidarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoCuidarTheme {
                AppNavHost(playServicesOk = true)
            }
        }
    }
}

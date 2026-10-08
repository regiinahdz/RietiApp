package com.example.rietiapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rietiapp.ui.theme.RietiAppTheme
import com.example.rietiapp.view.navigation.AppNavigation
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Limpiar caché anterior de tiles bloqueados
        try {
            cacheDir.resolve("osmdroid").deleteRecursively()
            filesDir.resolve("osmdroid").deleteRecursively()
            getDatabasePath("osmdroid.db")?.delete()
        } catch (_: Exception) {}

        Configuration.getInstance().userAgentValue = "Mozilla/5.0 (Android; Mobile)"
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osm_pref", MODE_PRIVATE))

        enableEdgeToEdge()
        setContent {
            RietiAppTheme {
                AppNavigation()
            }
        }
    }
}
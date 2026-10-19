/**
 * Actividad principal de punto de entrada de la aplicación RIETI.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package mx.sipinna.rietiapp.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import mx.sipinna.rietiapp.ui.theme.RietiAppTheme
import mx.sipinna.rietiapp.view.navigation.AppNavigation
import org.osmdroid.config.Configuration

/**
 * Actividad principal [MainActivity] que configura la inicialización de mapas
 * osmdroid y monta el árbol de composables de la navegación de Compose.
 */
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Limpiar caché anterior de tiles bloqueados de osmdroid
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

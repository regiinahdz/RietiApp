/**
 * Componente ejecutable del Paso 2 del formulario de reporte (Arquitectura MVVM).
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package mx.sipinna.rietiapp.view.screens.formularioPantallas.pasos

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.sipinna.rietiapp.view.components.OsmMapView
import mx.sipinna.rietiapp.viewmodel.UbicacionViewModel

/**
 * Muestra un mapa interactivo basado en OpenStreetMap/osmdroid para la selección de ubicación geográfica,
 * delegando la lógica de geolocalización y geocodificación al [UbicacionViewModel] (MVVM).
 *
 * @param fotosUris Lista de [Uri]s locales con las fotografías seleccionadas.
 * @param onFotosChange Callback al modificar la lista de fotos.
 * @param modifier Modificador composable opcional.
 * @param ubicacionDireccion Dirección del lugar resuelta por geocodificación.
 * @param onUbicacionChange Callback invocado al seleccionar un punto en el mapa con latitud, longitud, dirección y municipio.
 * @param referencias Texto con referencias adicionales ingresadas por el usuario.
 * @param onReferenciasChange Callback al editar las referencias del lugar.
 * @param ubicacionViewModel Instancia de [UbicacionViewModel] que gestiona los datos de ubicación.
 */
@Composable
fun Paso2Ubicacion(
    fotosUris: List<Uri>,
    onFotosChange: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier,
    ubicacionDireccion: String = "",
    onUbicacionChange: (lat: Double, lng: Double, direccion: String, municipio: String) -> Unit = { _, _, _, _ -> },
    referencias: String = "",
    onReferenciasChange: (String) -> Unit = {},
    ubicacionViewModel: UbicacionViewModel = viewModel()
) {
    val context = LocalContext.current
    val estadoUbicacion = ubicacionViewModel.estadoUbicacion

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            ubicacionViewModel.solicitarUbicacionActual(context, onUbicacionChange)
        }
    }

    LaunchedEffect(Unit) {
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if ((hasFine || hasCoarse) && ubicacionDireccion.isBlank()) {
            ubicacionViewModel.solicitarUbicacionActual(context, onUbicacionChange)
        }
    }

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "2. ¿Dónde ocurrió?",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Toca un punto en el mapa para marcar el lugar del reporte, o usa el botón de GPS si estás en el sitio.",
                style = MaterialTheme.typography.bodyMedium
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Toca cualquier punto del mapa para fijar el lugar:",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OsmMapView(
                            selectedGeoPoint = estadoUbicacion.geoPoint,
                            onLocationSelected = { geoPoint ->
                                ubicacionViewModel.seleccionarUbicacion(context, geoPoint, onUbicacionChange)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }

                    SmallFloatingActionButton(
                        onClick = {
                            val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

                            if (hasFine || hasCoarse) {
                                ubicacionViewModel.solicitarUbicacionActual(context, onUbicacionChange)
                            } else {
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Usar mi ubicación actual"
                        )
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Ubicación seleccionada:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        if (estadoUbicacion.estaCargandoDireccion) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                                Text(
                                    text = "Obteniendo dirección...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        } else {
                            Text(
                                text = if (ubicacionDireccion.isNotBlank()) ubicacionDireccion else "Ninguna (Toca un punto en el mapa)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (ubicacionDireccion.isNotBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            if (ubicacionDireccion.isBlank()) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = "Para avanzar al siguiente paso es necesario marcar un punto en el mapa.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            OutlinedTextField(
                value = referencias,
                onValueChange = onReferenciasChange,
                label = { Text("Referencias del lugar (opcional)") },
                placeholder = { Text("Ej. Entre calle A y B, frente al parque, fachada color azul...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                singleLine = false,
                supportingText = {
                    Text("Detalles visuales para localizar el punto exacto.")
                }
            )

            SeccFotosReferencia(
                fotosUris = fotosUris,
                onFotosChange = onFotosChange
            )
        }
    }
}

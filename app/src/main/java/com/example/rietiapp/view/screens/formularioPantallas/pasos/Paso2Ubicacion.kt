package com.example.rietiapp.view.screens.formularioPantallas.pasos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@Composable
fun Paso2Ubicacion(
    referencias: String = "",
    onReferenciasChange: (String) -> Unit = {},
    fotosUris: List<Uri>,
    onFotosChange: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier,
    ubicacionDireccion: String = "",
    onUbicacionChange: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedPoint by remember { mutableStateOf<GeoPoint?>(null) }
    var estaCargandoDireccion by remember { mutableStateOf(false) }

    val seleccionarUbicacion: (GeoPoint) -> Unit = { geoPoint ->
        selectedPoint = geoPoint
        estaCargandoDireccion = true
        onUbicacionChange("Buscando dirección...")
        coroutineScope.launch {
            val direccion = obtenerDireccion(context, geoPoint)
            estaCargandoDireccion = false
            onUbicacionChange(direccion)
        }
    }

    val solicitarUbicacionActual: () -> Unit = {
        obtenerUbicacionActual(context) { geoPoint ->
            seleccionarUbicacion(geoPoint)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            solicitarUbicacionActual()
        }
    }

    LaunchedEffect(Unit) {
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (hasFine || hasCoarse) {
            solicitarUbicacionActual()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
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
                text = "Proporciona la ubicación aproximada donde observaste la situación.",
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
                            text = "Selecciona tu ubicación en el mapa (toca un punto):",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        OsmMapView(
                            selectedGeoPoint = selectedPoint,
                            onLocationSelected = seleccionarUbicacion,
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
                                solicitarUbicacionActual()
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
                            contentDescription = "Mi ubicación actual"
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
                        if (estaCargandoDireccion) {
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
                                text = if (ubicacionDireccion.isNotBlank()) ubicacionDireccion else "Toca un punto en el mapa para fijar la ubicación",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (ubicacionDireccion.isNotBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

            // CAMPO DE REFERENCIAS DE LUGAR
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
                    Text("Describe detalles visuales útiles para encontrar el punto exacto.")
                }
            )

            // Asegúrate de tener la función SeccFotosReferencia definida en tu proyecto
            SeccFotosReferencia(
                fotosUris = fotosUris,
                onFotosChange = onFotosChange
            )
        }
    }
}

private val ESRI_WORLD_STREET_MAP = object : OnlineTileSourceBase(
    "EsriWorldStreetMap",
    0, 19, 256, "",
    arrayOf("https://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/")
) {
    override fun getTileURLString(pMapTileIndex: Long): String {
        return baseUrl + MapTileIndex.getZoom(pMapTileIndex) + "/" + MapTileIndex.getY(pMapTileIndex) + "/" + MapTileIndex.getX(pMapTileIndex)
    }
}

@Composable
fun OsmMapView(
    selectedGeoPoint: GeoPoint?,
    onLocationSelected: (GeoPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    DisposableEffect(context) {
        try {
            context.cacheDir.resolve("osmdroid").deleteRecursively()
            context.filesDir.resolve("osmdroid").deleteRecursively()
            context.getDatabasePath("osmdroid.db")?.delete()
        } catch (_: Exception) {}

        Configuration.getInstance().userAgentValue = "RietiAppMobile/1.0"
        Configuration.getInstance().load(context, context.getSharedPreferences("osm_pref", Context.MODE_PRIVATE))
        onDispose { }
    }

    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { ctx ->
            Configuration.getInstance().userAgentValue = "RietiAppMobile/1.0"
            Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osm_pref", Context.MODE_PRIVATE))

            MapView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                setTileSource(ESRI_WORLD_STREET_MAP)
                setMultiTouchControls(true)

                controller.setZoom(15.0)
                controller.setCenter(GeoPoint(19.4326, -99.1332))

                setOnTouchListener { view, event ->
                    when (event.actionMasked) {
                        android.view.MotionEvent.ACTION_DOWN,
                        android.view.MotionEvent.ACTION_POINTER_DOWN -> {
                            view.parent.requestDisallowInterceptTouchEvent(true)
                        }
                        android.view.MotionEvent.ACTION_UP,
                        android.view.MotionEvent.ACTION_CANCEL -> {
                            view.parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    false
                }

                val receiver = object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                        onLocationSelected(p)
                        return true
                    }

                    override fun longPressHelper(p: GeoPoint): Boolean {
                        return false
                    }
                }
                overlays.add(MapEventsOverlay(receiver))

                onResume()
            }
        },
        update = { mapView ->
            mapView.overlays.removeAll { it is Marker }

            if (selectedGeoPoint != null) {
                val marker = Marker(mapView).apply {
                    position = selectedGeoPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Ubicación seleccionada"
                }
                mapView.overlays.add(marker)
                mapView.controller.animateTo(selectedGeoPoint)
            }

            mapView.requestLayout()
            mapView.invalidate()
        }
    )
}

private fun obtenerUbicacionActual(
    context: Context,
    onUbicacionObtenida: (GeoPoint) -> Unit
) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager ?: return
    val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    if (hasFine || hasCoarse) {
        val lastGps = try { locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) } catch (_: Exception) { null }
        val lastNetwork = try { locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) } catch (_: Exception) { null }
        val bestLocation = lastGps ?: lastNetwork

        if (bestLocation != null) {
            onUbicacionObtenida(GeoPoint(bestLocation.latitude, bestLocation.longitude))
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                LocationManager.GPS_PROVIDER
            } else LocationManager.NETWORK_PROVIDER
            try {
                locationManager.getCurrentLocation(
                    provider,
                    null,
                    context.mainExecutor
                ) { location ->
                    if (location != null) {
                        onUbicacionObtenida(GeoPoint(location.latitude, location.longitude))
                    }
                }
            } catch (_: Exception) {}
        }
    }
}

private suspend fun obtenerDireccion(context: Context, geoPoint: GeoPoint): String = withContext(Dispatchers.IO) {
    try {
        val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
        @Suppress("DEPRECATION")
        val addresses = geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            val addr = addresses[0]
            val street = addr.thoroughfare ?: addr.getFeatureName() ?: ""
            val subLocality = addr.subLocality ?: addr.subAdminArea ?: ""
            val locality = addr.locality ?: ""
            val adminArea = addr.adminArea ?: ""
            val parts = listOf(street, subLocality, locality, adminArea).filter { it.isNotBlank() }
            if (parts.isNotEmpty()) {
                return@withContext parts.joinToString(", ")
            }
            val addressLine = addr.getAddressLine(0)
            if (!addressLine.isNullOrBlank()) {
                return@withContext addressLine
            }
        }
    } catch (_: Exception) {}

    try {
        val urlString = "https://nominatim.openstreetmap.org/reverse?format=json&lat=${geoPoint.latitude}&lon=${geoPoint.longitude}"
        val url = java.net.URL(urlString)
        val conn = url.openConnection() as java.net.HttpURLConnection
        conn.setRequestProperty("User-Agent", "RietiAppMobile/1.0")
        conn.connectTimeout = 4000
        conn.readTimeout = 4000
        if (conn.responseCode == 200) {
            val response = conn.inputStream.bufferedReader().readText()
            val regex = """"display_name":\s*"([^"]+)"""".toRegex()
            val match = regex.find(response)
            if (match != null) {
                return@withContext match.groupValues[1]
            }
        }
    } catch (_: Exception) {}

    return@withContext "Latitud: %.4f, Longitud: %.4f".format(geoPoint.latitude, geoPoint.longitude)
}
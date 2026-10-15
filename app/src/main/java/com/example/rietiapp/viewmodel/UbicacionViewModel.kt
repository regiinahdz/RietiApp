package com.example.rietiapp.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rietiapp.model.datos.UbicacionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

/**
 * ViewModel que controla la lógica de geolocalización, obtención de GPS y resolución de direcciones.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
class UbicacionViewModel : ViewModel() {

    /**
     * Estado observable de los datos de ubicación seleccionados.
     */
    var estadoUbicacion by mutableStateOf(UbicacionData())
        private set

    /**
     * Actualiza el punto de ubicación geográfica e inicia la resolución asíncrona de dirección.
     *
     * @param context Contexto de la aplicación.
     * @param geoPoint Punto [GeoPoint] seleccionado.
     * @param onUbicacionChange Callback invocado al completar la resolución de datos.
     */
    fun seleccionarUbicacion(
        context: Context,
        geoPoint: GeoPoint,
        onUbicacionChange: (lat: Double, lng: Double, dir: String, municipio: String) -> Unit = { _, _, _, _ -> }
    ) {
        estadoUbicacion = estadoUbicacion.copy(
            geoPoint = geoPoint,
            direccion = "Buscando dirección...",
            estaCargandoDireccion = true
        )

        viewModelScope.launch {
            val (direccionObtenida, municipioObtenido) = obtenerDireccionYMunicipio(context, geoPoint)
            estadoUbicacion = estadoUbicacion.copy(
                direccion = direccionObtenida,
                municipio = municipioObtenido,
                estaCargandoDireccion = false
            )
            onUbicacionChange(geoPoint.latitude, geoPoint.longitude, direccionObtenida, municipioObtenido)
        }
    }

    /**
     * Solicita la posición GPS actual del dispositivo si se cuentan con los permisos requeridos.
     *
     * @param context Contexto de la aplicación.
     * @param onUbicacionChange Callback invocado con los resultados de ubicación.
     */
    fun solicitarUbicacionActual(
        context: Context,
        onUbicacionChange: (lat: Double, lng: Double, dir: String, municipio: String) -> Unit = { _, _, _, _ -> }
    ) {
        obtenerPosicionGps(context) { geoPoint ->
            seleccionarUbicacion(context, geoPoint, onUbicacionChange)
        }
    }

    /**
     * Obtiene las coordenadas GPS actuales del sensor del dispositivo.
     *
     * @param context Contexto del sistema.
     * @param onUbicacionObtenida Callback ejecutado con las coordenadas [GeoPoint].
     */
    private fun obtenerPosicionGps(
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
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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

    /**
     * Resuelve la dirección textual y el municipio utilizando Geocoder con fallback a la API de Nominatim.
     *
     * @param context Contexto del sistema.
     * @param geoPoint Coordenadas [GeoPoint] a consultar.
     * @return Par conteniendo la dirección resuelta y el nombre del municipio.
     */
    private suspend fun obtenerDireccionYMunicipio(
        context: Context,
        geoPoint: GeoPoint
    ): Pair<String, String> = withContext(Dispatchers.IO) {
        var direccionFinal = "Latitud: %.4f, Longitud: %.4f".format(geoPoint.latitude, geoPoint.longitude)
        var municipioFinal = ""

        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val addr = addresses[0]
                val street = addr.thoroughfare ?: addr.featureName ?: ""
                val subLocality = addr.subLocality ?: addr.subAdminArea ?: ""
                val locality = addr.locality ?: ""
                val adminArea = addr.adminArea ?: ""
                val parts = listOf(street, subLocality, locality, adminArea).filter { it.isNotBlank() }

                if (parts.isNotEmpty()) {
                    direccionFinal = parts.joinToString(", ")
                } else {
                    val addressLine = addr.getAddressLine(0)
                    if (!addressLine.isNullOrBlank()) {
                        direccionFinal = addressLine
                    }
                }

                municipioFinal = addr.locality ?: addr.subAdminArea ?: addr.subLocality ?: ""
                if (direccionFinal.isNotBlank()) {
                    return@withContext Pair(direccionFinal, municipioFinal)
                }
            }
        } catch (_: Exception) {}

        try {
            val urlString = "https://nominatim.openstreetmap.org/reverse?format=json&lat=${geoPoint.latitude}&lon=${geoPoint.longitude}"
            val url = URL(urlString)
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("User-Agent", "RietiAppMobile/1.0")
            conn.connectTimeout = 4000
            conn.readTimeout = 4000
            if (conn.responseCode == 200) {
                val response = conn.inputStream.bufferedReader().readText()
                val regexDisplayName = """"display_name":\s*"([^"]+)"""".toRegex()
                val matchDisplay = regexDisplayName.find(response)
                if (matchDisplay != null) {
                    direccionFinal = matchDisplay.groupValues[1]
                }

                val regexCity = """"city":\s*"([^"]+)"""".toRegex()
                val regexTown = """"town":\s*"([^"]+)"""".toRegex()
                val regexCounty = """"county":\s*"([^"]+)"""".toRegex()

                val matchCity = regexCity.find(response) ?: regexTown.find(response) ?: regexCounty.find(response)
                if (matchCity != null) {
                    municipioFinal = matchCity.groupValues[1]
                }
            }
        } catch (_: Exception) {}

        return@withContext Pair(direccionFinal, municipioFinal)
    }
}

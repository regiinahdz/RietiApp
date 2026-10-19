package mx.sipinna.rietiapp.model.datos

import org.osmdroid.util.GeoPoint

/**
 * Estado que almacena la información de localización geográfica seleccionada en el mapa.
 *
 * @property geoPoint Punto de coordenadas [GeoPoint] de osmdroid.
 * @property direccion Dirección de calle resuelta.
 * @property municipio Municipio resuelto.
 * @property estaCargandoDireccion Indicador de geocodificación inversa en proceso.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class UbicacionData(
    val geoPoint: GeoPoint? = null,
    val direccion: String = "",
    val municipio: String = "",
    val estaCargandoDireccion: Boolean = false
) {
    /**
     * Latitud obtenida del [geoPoint].
     */
    val latitud: Double
        get() = geoPoint?.latitude ?: 0.0

    /**
     * Longitud obtenida del [geoPoint].
     */
    val longitud: Double
        get() = geoPoint?.longitude ?: 0.0
}

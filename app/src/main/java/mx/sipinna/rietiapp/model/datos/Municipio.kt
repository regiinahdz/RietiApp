package mx.sipinna.rietiapp.model.datos

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que representa a un municipio en el catálogo del sistema.
 *
 * @property id Identificador único del municipio.
 * @property nombre Nombre oficial del municipio.
 * @property siglas Siglas de identificación del municipio.
 * @property claveInegi Clave oficial asignada por INEGI.
 * @property adheridoRieti Indicador numérico de adhesión a la plataforma RIETI.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class Municipio(
    @SerializedName("idMunicipio")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("siglas")
    val siglas: String? = null,

    @SerializedName("clave_inegi")
    val claveInegi: String? = null,

    @SerializedName("adherido_rieti")
    val adheridoRieti: Int = 0
)

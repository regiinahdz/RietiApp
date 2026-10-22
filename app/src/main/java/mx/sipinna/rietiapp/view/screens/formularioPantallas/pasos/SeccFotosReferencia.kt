package mx.sipinna.rietiapp.view.screens.formularioPantallas.pasos

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File

@Composable
fun SeccFotosReferencia(
    fotosUris: List<Uri>,
    onFotosChange: (List<Uri>) -> Unit
) {
    val context = LocalContext.current
    val maxFotos = 2

    // Estado para controlar el diálogo de elección (Cámara vs Galería)
    var mostrarOpciones by remember { mutableStateOf(false) }

    // Estado para almacenar la Uri temporal cuando se toma una foto
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // 1. Launcher para seleccionar imágenes de la Galería
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = maxFotos)
    ) { uris ->
        if (uris.isNotEmpty()) {
            val fotosRestantes = maxFotos - fotosUris.size
            if (fotosRestantes > 0) {
                val nuevas = uris.take(fotosRestantes)
                val combinadas = (fotosUris + nuevas).distinct().take(maxFotos)
                onFotosChange(combinadas)
            }
        }
    }

    // 2. Launcher para tomar foto con la Cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exitoso ->
        if (exitoso && tempCameraUri != null) {
            if (fotosUris.size < maxFotos) {
                val combinadas = (fotosUris + tempCameraUri!!).distinct().take(maxFotos)
                onFotosChange(combinadas)
            }
        }
    }

    // Función auxiliar para abrir la cámara
    fun abrirCamara() {
        if (fotosUris.size >= maxFotos) return
        try {
            val photoFile = File.createTempFile(
                "JPEG_${System.currentTimeMillis()}_",
                ".jpg",
                context.cacheDir
            )
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile
            )
            tempCameraUri = uri
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 3. Launcher para solicitar permiso de Cámara en tiempo de ejecución
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            abrirCamara()
        }
    }

    // Lógica para verificar permisos antes de capturar foto
    fun ejecutarCamaraConPermiso() {
        val tienePermiso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            abrirCamara()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Fotografías de evidencia (opcional, máx. $maxFotos)",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Button(
                    onClick = { mostrarOpciones = true },
                    enabled = fotosUris.size < maxFotos
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Agregar Foto"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Adjuntar foto")
                }

                // Menú desplegable para elegir entre tomar foto o galería
                DropdownMenu(
                    expanded = mostrarOpciones,
                    onDismissRequest = { mostrarOpciones = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Tomar foto") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            mostrarOpciones = false
                            ejecutarCamaraConPermiso()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Seleccionar de galería") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            mostrarOpciones = false
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }
            }

            Text(
                text = "${fotosUris.size}/$maxFotos",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (fotosUris.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(fotosUris) { uri ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Evidencia",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        IconButton(
                            onClick = {
                                onFotosChange(fotosUris.filter { it != uri })
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
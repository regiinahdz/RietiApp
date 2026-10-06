package com.example.rietiapp.view.screens.formularioPantallas.pasos

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SeccFotosReferencia(
    fotosUris: List<Uri>,
    onFotosChange: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier,
    maxFotos: Int = 2
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = maxFotos)
    ) { uris ->
        if (uris.isNotEmpty()) {
            val listaActualizada = (fotosUris + uris).take(maxFotos)
            onFotosChange(listaActualizada)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Fotografías de referencia (Opcional)",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Banner explicativo sobre dignidad e integridad
        Surface(
            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Aviso importante",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 2.dp, end = 8.dp)
                )
                Text(
                    text = "Las fotos son únicamente de referencia del lugar (fachadas, calles o puntos de referencia). Por respeto, dignidad y protección de derechos, no tomes fotos donde aparezcan personas.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Fotos adjuntas: ${fotosUris.size}/$maxFotos",
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Miniaturas y botón de adjuntar
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            fotosUris.forEachIndexed { index, uri ->
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = uri,
                        contentDescription = "Foto $index",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = {
                            val listaFiltrada = fotosUris.filterIndexed { i, _ -> i != index }
                            onFotosChange(listaFiltrada)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(24.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Eliminar foto",
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }
                }
            }

            if (fotosUris.size < maxFotos) {
                OutlinedButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.size(90.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adjuntar foto"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Adjuntar",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}
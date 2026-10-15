/**
 * Modificador composable de estilos visuales para efectos Claymorphic.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package com.example.rietiapp.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Aplica el efecto visual de Claymorphism con sombras interiores y exteriores infladas 3D.
 *
 * @param backgroundColor Color de fondo del elemento.
 * @param cornerRadius Radio de curvatura de los bordes.
 * @param elevation Elevación de la sombra.
 * @param pressed Indicador del estado presionado/hundido.
 * @return Instancia modificada de [Modifier].
 */
fun Modifier.clay(
    backgroundColor: Color,
    cornerRadius: Dp = 32.dp,
    elevation: Dp = 16.dp,
    pressed: Boolean = false
) = this.composed {
    this.drawBehind {
        val cornerRadiusPx = cornerRadius.toPx()
        val elevationPx = elevation.toPx()

        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.isAntiAlias = true

            if (!pressed) {
                frameworkPaint.color = backgroundColor.toArgb()
                frameworkPaint.setShadowLayer(
                    elevationPx * 1.2f,
                    elevationPx / 1.5f,
                    elevationPx / 1.5f,
                    android.graphics.Color.argb(30, 0, 0, 0)
                )
                canvas.drawRoundRect(
                    0f, 0f, size.width, size.height,
                    cornerRadiusPx, cornerRadiusPx, paint
                )
            }

            frameworkPaint.clearShadowLayer()
            frameworkPaint.color = backgroundColor.toArgb()
            canvas.drawRoundRect(
                0f, 0f, size.width, size.height,
                cornerRadiusPx, cornerRadiusPx, paint
            )

            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = 0f,
                        top = 0f,
                        right = size.width,
                        bottom = size.height,
                        cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                    )
                )
            }

            clipPath(path) {
                frameworkPaint.style = android.graphics.Paint.Style.STROKE
                frameworkPaint.strokeWidth = elevationPx * 1.5f
                frameworkPaint.color = android.graphics.Color.TRANSPARENT

                if (!pressed) {
                    frameworkPaint.setShadowLayer(
                        elevationPx * 0.8f,
                        -elevationPx / 3,
                        -elevationPx / 3,
                        android.graphics.Color.argb(30, 0, 0, 0)
                    )
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        cornerRadiusPx, cornerRadiusPx, paint
                    )

                    frameworkPaint.setShadowLayer(
                        elevationPx * 1.2f,
                        elevationPx / 3,
                        elevationPx / 3,
                        android.graphics.Color.argb(200, 255, 255, 255)
                    )
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        cornerRadiusPx, cornerRadiusPx, paint
                    )
                } else {
                    frameworkPaint.setShadowLayer(
                        elevationPx,
                        elevationPx / 2,
                        elevationPx / 2,
                        android.graphics.Color.argb(60, 0, 0, 0)
                    )
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        cornerRadiusPx, cornerRadiusPx, paint
                    )

                    frameworkPaint.setShadowLayer(
                        elevationPx,
                        -elevationPx / 2,
                        -elevationPx / 2,
                        android.graphics.Color.WHITE
                    )
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        cornerRadiusPx, cornerRadiusPx, paint
                    )
                }
            }
        }
    }
}

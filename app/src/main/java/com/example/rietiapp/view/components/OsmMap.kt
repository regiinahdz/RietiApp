package com.example.rietiapp.view.components

import android.content.Context
import org.osmdroid.config.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView


@Composable
fun OsmMap() {

    AndroidView(

        factory = { context: Context ->

            Configuration.getInstance().load(
                context,
                context.getSharedPreferences(
                    "osm",
                    Context.MODE_PRIVATE
                )
            )

            Configuration.getInstance().osmdroidBasePath =
                context.filesDir

            Configuration.getInstance().osmdroidTileCache =
                context.cacheDir

            Configuration.getInstance().userAgentValue =
                "RIETI-App/1.0"

            MapView(context).apply {

                setMultiTouchControls(true)

                controller.setZoom(18.0)
            }
        }
    )
}
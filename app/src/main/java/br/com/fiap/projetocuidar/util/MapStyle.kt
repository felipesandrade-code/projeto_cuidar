// MapStyle.kt
package br.com.fiap.projetocuidar.ui

import com.google.android.gms.maps.model.MapStyleOptions

object CleanMapStyle {
    val options: MapStyleOptions by lazy {
        MapStyleOptions(
            """
            [
              {"featureType":"poi","stylers":[{"visibility":"off"}]},
              {"featureType":"poi.business","stylers":[{"visibility":"off"}]},
              {"featureType":"poi.park","stylers":[{"visibility":"off"}]},
              {"featureType":"poi.school","stylers":[{"visibility":"off"}]},
              {"featureType":"transit","stylers":[{"visibility":"off"}]},
              {"featureType":"administrative","elementType":"labels","stylers":[{"visibility":"off"}]},
              {"featureType":"road","elementType":"labels.icon","stylers":[{"visibility":"off"}]},
              {"featureType":"water","stylers":[{"saturation":-10},{"lightness":10}]},
              {"featureType":"landscape","stylers":[{"saturation":-20}]}
            ]
            """.trimIndent()
        )
    }
}

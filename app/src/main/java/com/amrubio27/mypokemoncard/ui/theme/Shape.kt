package com.amrubio27.mypokemoncard.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class SlantedRectangleShape(private val slant: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Mover al punto superior izquierdo, con un desplazamiento para la inclinación
            moveTo(slant, 0f)
            // Línea al punto superior derecho
            lineTo(size.width, 0f)
            // Línea al punto inferior derecho, restando la inclinación
            lineTo(size.width - slant, size.height)
            // Línea al punto inferior izquierdo
            lineTo(0f, size.height)
            // Cerrar el trazado para volver al inicio
            close()
        }
        return Outline.Generic(path)
    }
}
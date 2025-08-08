package com.amrubio27.mypokemoncard.old.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.old.components.CapaEfectosBase
import com.amrubio27.mypokemoncard.old.components.CapaEfectosExtra
import com.amrubio27.mypokemoncard.old.components.CapaFondo
import com.amrubio27.mypokemoncard.old.components.CapaMarco
import com.amrubio27.mypokemoncard.old.components.CapaPokemonDelante
import com.amrubio27.mypokemoncard.old.components.CapaPokemonFondo
import com.amrubio27.mypokemoncard.old.components.CapaUIDatos
import com.amrubio27.mypokemoncard.old.domain.CartaDimensiones
import com.amrubio27.mypokemoncard.old.domain.Pokemon
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max

/**
 * Composable principal de la carta Pokémon TCG
 * Con efectos 3D de inclinación y perspectiva
 */
@Composable
fun MyPokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier, // Ancho fijo para mantener proporciones
    maxWidth: Dp = 350.dp // Aumentado para carta más grande
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    // Calcular dimensiones manteniendo la proporción de carta real
    val cartaAncho = minOf(maxWidth, (configuration.screenWidthDp * 0.9f).dp)
    val cartaAlto = cartaAncho / CartaDimensiones.RATIO_CARTA

    // Estados de animación para la inclinación 3D
    val rotacionX = remember { Animatable(0f) }
    val rotacionY = remember { Animatable(0f) }
    val escala = remember { Animatable(1f) }

    val coroutineScope = rememberCoroutineScope()

    // Límites de rotación (en grados)
    val maxRotacion = 30f

    // Función para calcular el offset de paralaje según la profundidad de la capa
    fun calcularParalaje(profundidad: Float): Pair<Float, Float> {
        val factorParalaje = profundidad * 0.5f // Ajustable para controlar intensidad
        val offsetX = rotacionY.value * factorParalaje
        val offsetY = rotacionX.value * factorParalaje
        return Pair(offsetX, offsetY)
    }

    Box(modifier = modifier
        .size(cartaAncho, cartaAlto)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    // Al soltar, animamos la carta para que vuelva a su posición original.
                    coroutineScope.launch {
                        launch {
                            rotacionX.animateTo(
                                targetValue = 0f, animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                        launch {
                            rotacionY.animateTo(
                                targetValue = 0f, animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                        launch {
                            escala.animateTo(
                                targetValue = 1f, animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                    }
                }) { change, dragAmount ->
                change.consume()

                val factorSensibilidad = 0.1f // Sensibilidad del arrastre

                coroutineScope.launch {
                    // Acumulamos el arrastre a la rotación actual
                    val nuevaRotX = rotacionX.value - dragAmount.y * factorSensibilidad
                    val nuevaRotY = rotacionY.value + dragAmount.x * factorSensibilidad

                    // Aplicamos la nueva rotación, restringiéndola a los límites.
                    rotacionX.snapTo(nuevaRotX.coerceIn(-maxRotacion, maxRotacion))
                    rotacionY.snapTo(nuevaRotY.coerceIn(-maxRotacion, maxRotacion))

                    // La escala sigue dependiendo de la inclinación actual.
                    val intensidad = max(abs(rotacionX.value), abs(rotacionY.value)) / maxRotacion
                    escala.snapTo(1f + intensidad * 0.15f)
                }
            }
        }
        .graphicsLayer(
            rotationX = rotacionX.value,
            rotationY = rotacionY.value,
            scaleX = escala.value,
            scaleY = escala.value,
            cameraDistance = 8f * density.density // Distancia de la cámara para el efecto 3D
        )
        .clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {

        // Capa 1: Fondo de la carta (más alejada, paralaje máximo)
        val (fondoOffsetX, fondoOffsetY) = calcularParalaje(1f)
        CapaFondo(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(fondoOffsetX.dp, fondoOffsetY.dp)
        )

        // Capa 2: Pokémon Fondo (parte trasera)
        val (pokemonFondoOffsetX, pokemonFondoOffsetY) = calcularParalaje(1.6f)
        CapaPokemonFondo(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(pokemonFondoOffsetX.dp, pokemonFondoOffsetY.dp)
        )

        // Capa 3: Marco de la carta (base estática)
        CapaMarco(
            pokemon = pokemon,
            modifier = Modifier.fillMaxSize(),
            cartaAncho = cartaAncho,
            cartaAlto = cartaAlto
        )

        // Capa 5: Pokémon Delante (parte frontal que sobresale, paralaje opuesto, DEBAJO de UI)
        val (pokemonDelanteOffsetX, pokemonDelanteOffsetY) = calcularParalaje(2f)
        CapaPokemonDelante(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(pokemonDelanteOffsetX.dp, pokemonDelanteOffsetY.dp)
        )

        // Capa 6: Efectos Base (paralaje medio, DEBAJO de UI)
        val (efectosBaseOffsetX, efectosBaseOffsetY) = calcularParalaje(1f)
        CapaEfectosBase(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(efectosBaseOffsetX.dp, efectosBaseOffsetY.dp)
        )

        // Capa 7: Efectos Extra (más cerca, paralaje mínimo, DEBAJO de UI)
        val (efectosExtraOffsetX, efectosExtraOffsetY) = calcularParalaje(0.5f)
        CapaEfectosExtra(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(efectosExtraOffsetX.dp, efectosExtraOffsetY.dp)
        )

        // Capa 4: UI de datos (sin paralaje, última para estar por encima)
        CapaUIDatos(
            pokemon = pokemon,
            modifier = Modifier.fillMaxSize(),
            cartaAncho = cartaAncho
        )
    }
}
package com.amrubio27.mypokemoncard.pokemonCardNew.view

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
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.CartaDimensiones
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.BackgroundLayer
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.BaseEffectsLayer
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.ExtraEffectsLayer
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.FrameLayer
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.PokeDevBackLayer
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.PokeDevFrontLayer
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.UISimplifiedLayer
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max

/**
 * Main composable for the Pokémon TCG card
 * With 3D tilt and perspective effects
 */
@Composable
fun MyPokemonCardNew(
    pokemon: com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon,
    modifier: Modifier = Modifier, // Ancho fijo para mantener proporciones
    maxWidth: Dp = 350.dp // Aumentado para carta más grande
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    // Calculate dimensions while maintaining the real card ratio
    val cartaAncho = minOf(maxWidth, (configuration.screenWidthDp * 0.9f).dp)
    val cartaAlto = cartaAncho / CartaDimensiones.RATIO_CARTA

    // Animation states for 3D tilt
    val rotacionX = remember { Animatable(0f) }
    val rotacionY = remember { Animatable(0f) }
    val escala = remember { Animatable(1f) }

    val coroutineScope = rememberCoroutineScope()

    // Maximum rotation limits for the 3D effect
    val maxRotacion = 30f

    // Function to calculate parallax effect based on depth
    fun calcularParalaje(profundidad: Float): Pair<Float, Float> {
        val factorParalaje = profundidad * 0.5f // Ajustable para controlar intensidad
        val offsetX = rotacionY.value * factorParalaje
        val offsetY = rotacionX.value * factorParalaje
        return Pair(offsetX, offsetY)
    }

    Box(
        modifier = modifier
            .size(cartaAncho, cartaAlto)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        // Reset animations when the drag ends
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

                    val factorSensibilidad = 0.1f // Sensibility of the drag

                    coroutineScope.launch {
                        // Accumulate the drag to the current rotation
                        val nuevaRotX = rotacionX.value - dragAmount.y * factorSensibilidad
                        val nuevaRotY = rotacionY.value + dragAmount.x * factorSensibilidad

                        // Clamp the rotation values to the maximum limits
                        rotacionX.snapTo(nuevaRotX.coerceIn(-maxRotacion, maxRotacion))
                        rotacionY.snapTo(nuevaRotY.coerceIn(-maxRotacion, maxRotacion))

                        // Calculate the intensity of the tilt for scaling
                        val intensidad =
                            max(abs(rotacionX.value), abs(rotacionY.value)) / maxRotacion
                        escala.snapTo(1f + intensidad * 0.15f)
                    }
                }
            }
            .graphicsLayer(
                rotationX = rotacionX.value,
                rotationY = rotacionY.value,
                scaleX = escala.value,
                scaleY = escala.value,
                cameraDistance = 8f * density.density // Camera distance for 3D effect
            )
            .clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {

        // Layer 1: Background (static background, no parallax)
        val (fondoOffsetX, fondoOffsetY) = calcularParalaje(1f)
        BackgroundLayer(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(fondoOffsetX.dp, fondoOffsetY.dp)
        )

        // Layer 2: Pokémon Background (parallax effect, behind UI)
        val (pokemonFondoOffsetX, pokemonFondoOffsetY) = calcularParalaje(1.6f)
        PokeDevBackLayer(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(pokemonFondoOffsetX.dp, pokemonFondoOffsetY.dp)
        )

        // Layer 3: Frame (static frame, no parallax)
        FrameLayer(
            pokemon = pokemon,
            modifier = Modifier.fillMaxSize(),
            cartaAncho = cartaAncho,
            cartaAlto = cartaAlto
        )

        // Layer 5: Pokémon in front (parallax effect, above UI)
        val (pokemonDelanteOffsetX, pokemonDelanteOffsetY) = calcularParalaje(2f)
        PokeDevFrontLayer(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(pokemonDelanteOffsetX.dp, pokemonDelanteOffsetY.dp)
        )

        // Layer 6: Base Effects (parallax effect, above Pokémon)
        val (efectosBaseOffsetX, efectosBaseOffsetY) = calcularParalaje(1f)
        BaseEffectsLayer(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(efectosBaseOffsetX.dp, efectosBaseOffsetY.dp)
        )

        // Layer 7: Extra Effects (parallax effect, above everything else)
        val (efectosExtraOffsetX, efectosExtraOffsetY) = calcularParalaje(0.5f)
        ExtraEffectsLayer(
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .offset(efectosExtraOffsetX.dp, efectosExtraOffsetY.dp)
        )

        // Layer 8: Simplified UI (always on top, no parallax)
        UISimplifiedLayer(
            pokemon = pokemon, modifier = Modifier.fillMaxSize(), cartaAncho = cartaAncho
        )
    }
}
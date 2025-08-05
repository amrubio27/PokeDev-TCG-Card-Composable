package com.amrubio27.mypokemoncard.pokemonCard.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCard.domain.Ataque
import com.amrubio27.mypokemoncard.pokemonCard.domain.CartaDimensiones
import com.amrubio27.mypokemoncard.pokemonCard.domain.Pokemon
import com.amrubio27.mypokemoncard.pokemonCard.domain.TipoPokemon
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
    modifier: Modifier = Modifier,
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

    // Scope de corrutinas de Compose
    val coroutineScope = rememberCoroutineScope()

    // Límites de rotación (en grados) - Aumentados para más dramatismo
    val maxRotacion = 30f

    // Función para normalizar las coordenadas del drag a rotaciones
    fun calcularRotacion(
        offset: Offset, tamaño: Size
    ): Pair<Float, Float> {
        val centroX = tamaño.width / 2f
        val centroY = tamaño.height / 2f

        // Calcular la distancia desde el centro como porcentaje
        val deltaX = (offset.x - centroX) / centroX
        val deltaY = (offset.y - centroY) / centroY

        // Aplicar amplificación para mayor sensibilidad
        val amplificacion = 1.8f

        // Convertir a rotaciones (invertir Y para que sea intuitivo)
        val rotY = deltaX * maxRotacion * amplificacion
        val rotX = -deltaY * maxRotacion * amplificacion

        return Pair(rotX, rotY)
    }

    // Función para calcular el offset de paralaje según la profundidad de la capa
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
                    onDragStart = {
                        // No es necesario hacer nada aquí para la lógica de arrastre continuo
                    },
                    onDragEnd = {
                        // Al soltar, animamos la carta para que vuelva a su posición original.
                        coroutineScope.launch {
                            launch {
                                rotacionX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                            launch {
                                rotacionY.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                            launch {
                                escala.animateTo(
                                    targetValue = 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()

                    val factorSensibilidad = 0.4f // Puedes ajustar este valor

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
                cameraDistance = 8f * density.density // Reducido para perspectiva más dramática
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

        // Capa 4: UI de datos (SIEMPRE ENCIMA DE TODO, sin paralaje, última para estar por encima)
        CapaUIDatos(
            pokemon = pokemon,
            modifier = Modifier.fillMaxSize(),
            cartaAncho = cartaAncho,
            cartaAlto = cartaAlto
        )
    }
}

/**
 * Capa 1: Fondo base de la carta
 */
@Composable
private fun CapaFondo(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Prioridad 1: Imagen de recurso local
        pokemon.fondoResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Fondo de carta",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            // Prioridad 2: Color de fallback
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp) // Padding para no cubrir el borde
                    .background(
                        pokemon.colorDominante.copy(alpha = 0.1f),
                        RoundedCornerShape(7.dp) // Radio menor para respetar el borde
                    )
            )
        }
    }
}

/**
 * Capa 2: Pokémon de fondo (parte trasera)
 */
@Composable
private fun CapaPokemonFondo(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Prioridad 1: Imagen de recurso local
        pokemon.pokemonFondoResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Pokémon de fondo",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(7.dp)), // Radio menor para respetar el borde
                contentScale = ContentScale.Fit,

                )
        } ?: pokemon.imagenPokemonFondo?.let { imagenUrl ->
            // Prioridad 2: URL remota (aquí iría la carga con Coil o similar)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp) // Padding para no cubrir el borde
                    .background(
                        pokemon.tipo.color.copy(alpha = 0.2f),
                        RoundedCornerShape(7.dp) // Radio menor para respetar el borde
                    )
            )
        }
    }
}

/**
 * Capa 3: Marco de la carta con SVG teñido según el tipo
 */
@Composable
private fun CapaMarco(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp, cartaAlto: Dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 12.dp,
                color = Color.DarkGray.copy(alpha = 1f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        // Si hay un SVG del marco, lo mostramos teñido con el color del tipo
        pokemon.marcoSvgResourceId?.let { svgResourceId ->
            Image(
                    imageVector = ImageVector.vectorResource(id = svgResourceId),
            contentDescription = "Marco de carta ${pokemon.tipo.nombreEs}",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(
                Color(0xFF00FFB0).copy(alpha = 0.5f) // Color del tipo Pokémon
            )
            )
            //Marco
            Image(
                painter = painterResource(id = R.drawable.marco2b),
                contentDescription = "Fondo de carta",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        } ?: run {
            // Fallback: marco simple si no hay SVG
            val recorteAlto = cartaAlto * CartaDimensiones.RATIO_RECORTE_SUPERIOR

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Área superior transparente (recorte para ver el Pokémon)
                Spacer(modifier = Modifier.height(recorteAlto))

                // Marco inferior con color del tipo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cartaAlto - recorteAlto)
                        .background(
                            pokemon.tipo.color.copy(alpha = 0.3f),
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp
                            )
                        )
                )
            }
        }
    }
}

/**
 * Capa 4: UI de datos de la carta (SIEMPRE por encima)
 */
@Composable
private fun CapaUIDatos(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp, cartaAlto: Dp
) {
    val recorteAlto = cartaAlto * CartaDimensiones.RATIO_RECORTE_SUPERIOR

    Box(
        modifier = modifier.fillMaxSize().offset(y=2.dp)
    ) {
        Column(
            modifier = modifier.padding(vertical = 12.dp, horizontal = 6.dp),
        ) {
            // Información superior
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                // Card para "BASIC"
                Text(
                    text = "JUNIOR",
                    fontSize = (cartaAncho.value / 40).sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .height(16.dp)
                        .absoluteOffset(y = (-4).dp)
                        .padding(horizontal = 6.dp)
                )

                Text(
                    text = pokemon.nombre,
                    fontSize = (cartaAncho.value / 16).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                )

                Spacer(modifier = Modifier.weight(1f))


                // Grupo de HP
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.offset(x = -12.dp) // Ajuste para que no se superponga con el marco
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.offset(y = (2).dp),
                            text = "HP",
                            fontSize = (cartaAncho.value / 30).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Column {
                        Text(
                            text = pokemon.hp.toString(),
                            fontSize = (cartaAncho.value / 15).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                //en vez de un icono una imagen
                Image(
                    painter = painterResource(id = R.drawable.ic_android),
                    contentDescription = "Fondo de carta",
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = -10.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        Color(0xFFFFFFFF)
                    ) // Tint para que se vea bien
                )
            }
        }
    }

    // Área central con Pokémon (recorte para ver el fondo)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp, end = 0.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {


            // Área central con ataques (después del área del Pokémon)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        bottom = 8.dp
                    ) // Reducido el padding ya que tenemos la ilustración
            ) {
                // Ataques
                pokemon.ataques.forEach { ataque ->
                    AtaqueUI(
                        ataque = ataque, textSize = (cartaAncho.value / 25).sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Información inferior pegada abajo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Debilidad y resistencia en una Card
                if (pokemon.debilidad != null || pokemon.resistencia != null) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height((cartaAncho.value / 17).dp)
                            .padding(horizontal = 2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(
                            2.dp, Color.Gray.copy(alpha = 0.2f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                pokemon.debilidad?.let { debilidad ->
                                    Text(
                                        text = "Debilidad",
                                        fontSize = (cartaAncho.value / 35).sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(y = -2.dp)
                                            .padding(end = 4.dp)
                                    )
                                    Text(
                                        text = "Lunes/Presencial",
                                        fontSize = (cartaAncho.value / 36).sp,
                                        color = debilidad.color,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.offset(y = -2.dp)
                                    )
                                }

                            }

                            pokemon.resistencia?.let { resistencia ->
                                if (pokemon.debilidad != null) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Resistencia",
                                        fontSize = (cartaAncho.value / 55).sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(y = -2.dp)
                                            .padding(end = 8.dp)
                                    )
                                    Text(
                                        text = resistencia.nombreEs,
                                        fontSize = (cartaAncho.value / 52).sp,
                                        color = resistencia.color,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.offset(y = -2.dp)
                                    )
                                }

                            }
                        }
                    }
                }

                // Costo de retirada en una Card separada
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp)
                        .height((cartaAncho.value / 17).dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(
                        2.dp, Color.Gray.copy(alpha = 0.2f)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Text(
                                text = "Fuerte",
                                fontSize = (cartaAncho.value / 35).sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .offset(y = -2.dp)
                                    .padding(end = 2.dp)
                            )
                            Text(
                                text = "Curiosidad/TeamWork", //text = pokemon.costoRetirada.toString()
                                fontSize = (cartaAncho.value / 35).sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.offset(y = -2.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rareza (número de iconos de rareza)
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    repeat(pokemon.id % 5 + 4) { // Simulamos rareza con el ID
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .size((cartaAncho.value / 19).dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                    }
                }

                // ReglaEx si la hubiera
                val descripcionTexto = pokemon.descripcion.ifEmpty {
                    "Sin descripción"
                }
                Text(
                    text = descripcionTexto,
                    fontSize = (cartaAncho.value / 30).sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(2.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                )
            }
        }
    }


}

/**
 * Componente para mostrar un ataque
 */
@Composable
private fun AtaqueUI(
    ataque: Ataque, textSize: TextUnit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {

            // Iconos de costo (simplificado por ahora)
            ataque.costo.forEach { tipo ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(tipo.color, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = ataque.nombre,
                fontSize = textSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = ataque.danio.toString(),
                fontSize = textSize * 1.3,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        if (ataque.descripcion.isNotEmpty()) {
            Text(
                text = ataque.descripcion,
                fontSize = textSize * 0.8f,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

/**
 * Capa 5: Pokémon delante (parte frontal que sobresale)
 */
@Composable
private fun CapaPokemonDelante(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Prioridad 1: Imagen de recurso local
        pokemon.pokemonDelanteResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Pokémon delante",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)), // Puede usar todo el espacio hasta el borde de la carta
                contentScale = ContentScale.Fit
            )
        } ?: pokemon.imagenPokemonDelante?.let { imagenUrl ->
            // Prioridad 2: URL remota (aquí iría la carga con Coil o similar)
            // Placeholder por ahora
        }
    }
}

/**
 * Capa 6: Efectos base
 */
@Composable
private fun CapaEfectosBase(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Prioridad 1: Imagen de recurso local
        pokemon.efectosBaseResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Efectos base",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)), // Puede usar todo el espacio hasta el borde de la carta
                contentScale = ContentScale.FillBounds,
                alpha = 1f // Efectos semi-transparentes
            )
        } ?: pokemon.efectosBase?.let { efectosUrl ->
            // Prioridad 2: URL remota (aquí iría la carga con Coil o similar)
        }
    }
}

/**
 * Capa 7: Efectos extra
 */
@Composable
private fun CapaEfectosExtra(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Prioridad 1: Imagen de recurso local
        pokemon.efectosExtraResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Efectos extra",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)), // Puede usar todo el espacio hasta el borde de la carta
                contentScale = ContentScale.FillBounds,
                alpha = 1f // Efectos más sutiles
            )
        } ?: pokemon.efectosExtra?.let { efectosUrl ->
            // Prioridad 2: URL remota (aquí iría la carga con Coil o similar)
        }
    }
}

/**
 * Composable para mostrar la carta centrada en pantalla completa
 * con fondo neutro y espacio adecuado
 */
@Composable
fun PokemonCardScreen(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E2E2E)), // Fondo neutro oscuro
        contentAlignment = Alignment.Center
    ) {
        MyPokemonCard(
            pokemon = pokemon,
            modifier = Modifier, // Margen para separar de los bordes
            maxWidth = 320.dp // Aumentado para carta más grande en pantalla
        )
    }
}

/**
 * Función de preview y ejemplo de uso
 */
@Composable
fun EjemploPokemonCard() {
    val pokemonEjemplo = Pokemon(
        id = 25,
        nombre = "Mobile Developer",
        tipo = TipoPokemon.PLANTA,
        hp = 120,
        nivel = 12,
        descripcion = "Mobile Dev. Open to work!!!",
        ataques = listOf(
            Ataque(
                nombre = "Motivación",
                costo = listOf(TipoPokemon.ELECTRICO),
                danio = 20,
                descripcion = "Aumenta la energía y la productividad"
            ), Ataque(
                nombre = "Autoaprendizaje",
                costo = listOf(TipoPokemon.PSIQUICO, TipoPokemon.NORMAL),
                danio = 40,
                descripcion = "Aprende nuevas tecnologías rápidamente"
            )
        ),
        debilidad = TipoPokemon.TIERRA,
        costoRetirada = 1,
        marcoSvgResourceId = R.drawable.marcocarta__1_, // Aquí irá el ID del recurso SVG cuando lo añadas
        colorDominante = TipoPokemon.ELECTRICO.color,
        imagenPokemonFondo = "https://c3.klipartz.com/pngpicture/564/11/sticker-png-pikachu-pokemon-pikachu-thumbnail.png", // URL o resource para la parte de atrás
        imagenPokemonDelante = "https://c3.klipartz.com/pngpicture/564/11/sticker-png-pikachu-pokemon-pikachu-thumbnail.png", // URL o resource para la parte de adelante
        efectosBase = "https://c3.klipartz.com/pngpicture/564/11/sticker-png-pikachu-pokemon-pikachu-thumbnail.png", // URL o resource para efectos base
        efectosExtra = "https://c3.klipartz.com/pngpicture/564/11/sticker-png-pikachu-pokemon-pikachu-thumbnail.png", // URL o resource para efectos extra
        pokemonFondoResourceId = R.drawable.perreteatras,
        pokemonDelanteResourceId = R.drawable.perretedelante3, // Aquí irá el ID del recurso de la parte delantera
        efectosBaseResourceId = R.drawable.efectos1c,
        efectosExtraResourceId = R.drawable.efectos2, // Aquí irá el ID del recurso de efectos extra
        fondoResourceId = R.drawable.fondo3
    )

    // Versión centrada en pantalla
    PokemonCardScreen(pokemon = pokemonEjemplo)
}

/**
 * Preview para desarrollo - carta sola
 */
@Composable
fun EjemploPokemonCardSola() {
    val pokemonEjemplo = Pokemon(
        id = 25,
        nombre = "Pikachu",
        tipo = TipoPokemon.ELECTRICO,
        hp = 60,
        nivel = 12,
        descripcion = "Pokémon Ratón Eléctrico",
        ataques = listOf(
            Ataque(
                nombre = "Impactrueno",
                costo = listOf(TipoPokemon.ELECTRICO),
                danio = 20,
                descripcion = "Lanza un rayo eléctrico"
            )
        ),
        debilidad = TipoPokemon.TIERRA,
        costoRetirada = 1,
        marcoSvgResourceId = R.drawable.marcocarta__1_, // Aquí irá el ID del recurso SVG cuando lo añadas
        colorDominante = TipoPokemon.ELECTRICO.color
    )

    MyPokemonCard(pokemon = pokemonEjemplo)
}
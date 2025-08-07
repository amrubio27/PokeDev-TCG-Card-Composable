package com.amrubio27.mypokemoncard.pokemonCard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.pokemonCard.domain.Pokemon

@Composable
fun CapaFondo(
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
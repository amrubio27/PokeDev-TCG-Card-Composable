package com.amrubio27.mypokemoncard.pokemonCardOld.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.pokemonCardOld.domain.Pokemon

@Composable
 fun CapaPokemonFondo(
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
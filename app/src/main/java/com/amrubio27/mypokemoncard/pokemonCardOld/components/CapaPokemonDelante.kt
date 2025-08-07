package com.amrubio27.mypokemoncard.pokemonCardOld.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.pokemonCardOld.domain.Pokemon

@Composable
fun CapaPokemonDelante(
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
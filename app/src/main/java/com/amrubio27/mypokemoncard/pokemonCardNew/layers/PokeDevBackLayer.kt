package com.amrubio27.mypokemoncard.pokemonCardNew.layers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon

@Composable
fun PokeDevBackLayer(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        pokemon.pokemonFondoResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Pokémon/Dev background layer",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}
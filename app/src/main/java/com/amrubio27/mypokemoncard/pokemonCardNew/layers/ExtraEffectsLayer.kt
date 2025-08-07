package com.amrubio27.mypokemoncard.pokemonCardNew.layers

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
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon

@Composable
fun ExtraEffectsLayer(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        pokemon.efectosExtraResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Extra Effects",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillBounds,
                alpha = 1f // Transparency
            )
        }
    }
}
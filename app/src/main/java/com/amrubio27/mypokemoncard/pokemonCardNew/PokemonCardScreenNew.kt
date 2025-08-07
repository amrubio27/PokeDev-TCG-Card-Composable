package com.amrubio27.mypokemoncard.pokemonCardNew

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon
import com.amrubio27.mypokemoncard.pokemonCardNew.view.MyPokemonCardNew

@Composable
fun PokemonCardScreenNew(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E2E2E)),
        contentAlignment = Alignment.Center
    ) {
        MyPokemonCardNew(
            pokemon = pokemon,
            maxWidth = 320.dp // Adjust as needed
        )
    }
}
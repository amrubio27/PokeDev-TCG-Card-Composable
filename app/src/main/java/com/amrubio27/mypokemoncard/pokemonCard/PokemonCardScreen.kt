package com.amrubio27.mypokemoncard.pokemonCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.pokemonCard.domain.Pokemon
import com.amrubio27.mypokemoncard.pokemonCard.view.MyPokemonCard

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
package com.amrubio27.mypokemoncard.pokemonCardNew

import androidx.compose.runtime.Composable
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Ataque
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.TipoPokemon

@Composable
fun ExamplePokemonCardNew() {
    val pokemonEjemplo = Pokemon(
        nombre = "Mobile Developer",
        tipo = TipoPokemon.CUSTOM,
        hp = 120,
        nivel = "JUNIOR",
        descripcion = "Mobile Dev. Open to work!!!",
        ataques = listOf(
            Ataque(
                nombre = "Motivation",
                costo = listOf(TipoPokemon.ELECTRICO),
                danio = 20,
                descripcion = "Boosts energy and productivity",
            ), Ataque(
                nombre = "Self-learning",
                costo = listOf(TipoPokemon.PSIQUICO, TipoPokemon.NORMAL),
                danio = 40,
                descripcion = "Learns new technologies quickly"
            )
        ),
        debilidad = "Lunes/Presencial", // Weakness: Mondays and in-person work
        resistencia = "Curiosidad/TeamWork", // Resistance: Curiosity and Team Work
        marcoSvgResourceId = R.drawable.marcocarta__1_,
        pokemonFondoResourceId = R.drawable.perreteatras,
        pokemonDelanteResourceId = R.drawable.perretedelante3,
        efectosBaseResourceId = R.drawable.efectos1c,
        efectosExtraResourceId = R.drawable.efectos2,
        fondoResourceId = R.drawable.fondo3
    )

    // Screen composable to display the Pokémon card
    PokemonCardScreenNew(pokemon = pokemonEjemplo)
}
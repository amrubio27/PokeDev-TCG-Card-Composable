package com.amrubio27.mypokemoncard.pokemonCard

import androidx.compose.runtime.Composable
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCard.domain.Ataque
import com.amrubio27.mypokemoncard.pokemonCard.domain.Pokemon
import com.amrubio27.mypokemoncard.pokemonCard.domain.TipoPokemon

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
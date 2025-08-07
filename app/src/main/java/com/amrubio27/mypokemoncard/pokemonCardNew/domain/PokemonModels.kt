package com.amrubio27.mypokemoncard.pokemonCardNew.domain

import androidx.compose.ui.graphics.Color

/**
 * DEVS MODEL
 */
data class Pokemon(
    val nombre: String,
    val tipo: TipoPokemon,
    val hp: Int,
    val nivel: String = "JUNIOR",
    val descripcion: String,
    val ataques: List<Ataque>,
    val debilidad: String = "Nothing",
    val resistencia: String = "Nothing",

    // Recursos de imágenes locales
    val fondoResourceId: Int? = null, // Resource ID para el fondo de la carta
    val pokemonFondoResourceId: Int? = null, // Resource ID para el Pokémon de fondo
    val pokemonDelanteResourceId: Int? = null, // Resource ID para el Pokémon de adelante
    val marcoSvgResourceId: Int? = null, // Resource ID del SVG del marco
    val efectosBaseResourceId: Int? = null, // Resource ID para efectos base
    val efectosExtraResourceId: Int? = null, // Resource ID para efectos extra
)

/**
 * TYPES
 */
enum class TipoPokemon(val nombreEs: String, val color: Color) {
    FUEGO("Fuego", Color(0xFFFF6B35)),
    AGUA("Agua", Color(0xFF4A90E2)),
    PLANTA("Planta", Color(0xFF7ED321)),
    ELECTRICO("Eléctrico", Color(0xFFF5A623)),
    PSIQUICO("Psíquico", Color(0xFFBD10E0)),
    LUCHA("Lucha", Color(0xFFD0021B)),
    VENENO("Veneno", Color(0xFF9013FE)),
    TIERRA("Tierra", Color(0xFF8B572A)),
    VOLADOR("Volador", Color(0xFF50E3C2)),
    BICHO("Bicho", Color(0xFF417505)),
    ROCA("Roca", Color(0xFF8E6A5B)),
    FANTASMA("Fantasma", Color(0xFF4A148C)),
    DRAGON("Dragón", Color(0xFF3F51B5)),
    SINIESTRO("Siniestro", Color(0xFF212121)),
    ACERO("Acero", Color(0xFF607D8B)),
    HADA("Hada", Color(0xFFE91E63)),
    NORMAL("Normal", Color(0xFF9E9E9E)),
    HIELO("Hielo", Color(0xFF81D4FA)),

    CUSTOM(
        "Custom",
        Color(0xFF00FFB0).copy(alpha = 0.5f)
    ) // Tipo personalizado para desarrolladores
}

/**
 * "ATTACKS"
 */
data class Ataque(
    val nombre: String,
    val costo: List<TipoPokemon>,
    val danio: Int,
    val descripcion: String = ""
)

/**
 * TCG Card - 6.3cm x 8.8cm
 */
object CartaDimensiones {
    const val RATIO_CARTA = 6.3f / 8.8f // ~0.716
    const val ANCHO_DP = 252 // 6.3cm ≈ 252dp a 160dpi
    const val ALTO_DP = 352 // 8.8cm ≈ 352dp a 160dpi
    
    // Proporciones para el recorte superior del marco
    const val RATIO_RECORTE_SUPERIOR = 0.4f // 40% del alto para el área del Pokémon
}
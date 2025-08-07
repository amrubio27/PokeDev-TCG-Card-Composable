package com.amrubio27.mypokemoncard.pokemonCardNew.layers.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.ui.theme.SlantedRectangleShape

@Composable
fun RuleEx(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape = SlantedRectangleShape(slant = 35f))
            .border(
                width = 3.dp, // Grosor del borde
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFDC814D), Color(0xFFF5F5F5)),
                    start = Offset(600f, 140f),
                    end = Offset(400f, 0f)
                ),
                shape = SlantedRectangleShape(slant = 35f)
            )

            .padding(3.dp)
    ) {
        content()
    }
}
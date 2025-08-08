package com.amrubio27.mypokemoncard.old.components

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
import com.amrubio27.mypokemoncard.old.domain.Pokemon

@Composable
fun CapaEfectosBase(
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Prioridad 1: Imagen de recurso local
        pokemon.efectosBaseResourceId?.let { resourceId ->
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "Efectos base",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)), // Puede usar todo el espacio hasta el borde de la carta
                contentScale = ContentScale.FillBounds,
                alpha = 1f // Efectos semi-transparentes
            )
        } ?: pokemon.efectosBase?.let { efectosUrl ->
            // Prioridad 2: URL remota (aquí iría la carga con Coil o similar)
        }
    }
}
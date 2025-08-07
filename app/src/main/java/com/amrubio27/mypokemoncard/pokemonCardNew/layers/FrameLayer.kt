package com.amrubio27.mypokemoncard.pokemonCardNew.layers

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon

@Composable
fun FrameLayer(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp, cartaAlto: Dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 12.dp,
                color = Color.DarkGray.copy(alpha = 1f),
                shape = RoundedCornerShape(20.dp),
            )
            .border(
                width = 12.dp,
                color = Color.DarkGray.copy(alpha = 1f),
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        pokemon.marcoSvgResourceId?.let { svgResourceId ->
            Image(
                imageVector = ImageVector.vectorResource(id = svgResourceId),
                contentDescription = "frame of ${pokemon.tipo.nombreEs}",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(
                    pokemon.tipo.color // Color type tint
                )
            )
            //Marco
            Image(
                painter = painterResource(id = R.drawable.marco2b),
                contentDescription = "frame 2",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}
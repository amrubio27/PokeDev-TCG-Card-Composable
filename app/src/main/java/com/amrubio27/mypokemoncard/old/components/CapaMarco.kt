package com.amrubio27.mypokemoncard.old.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.amrubio27.mypokemoncard.old.domain.CartaDimensiones
import com.amrubio27.mypokemoncard.old.domain.Pokemon

@Composable
 fun CapaMarco(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp, cartaAlto: Dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 12.dp,
                color = Color.DarkGray.copy(alpha = 1f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        // Si hay un SVG del marco, lo mostramos teñido con el color del tipo
        pokemon.marcoSvgResourceId?.let { svgResourceId ->
            Image(
                imageVector = ImageVector.vectorResource(id = svgResourceId),
                contentDescription = "Marco de carta ${pokemon.tipo.nombreEs}",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(
                    Color(0xFF00FFB0).copy(alpha = 0.5f) // Color del tipo Pokémon
                )
            )
            //Marco
            Image(
                painter = painterResource(id = R.drawable.marco2b),
                contentDescription = "Fondo de carta",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        } ?: run {
            // Fallback: marco simple si no hay SVG
            val recorteAlto = cartaAlto * CartaDimensiones.RATIO_RECORTE_SUPERIOR

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Área superior transparente (recorte para ver el Pokémon)
                Spacer(modifier = Modifier.height(recorteAlto))

                // Marco inferior con color del tipo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cartaAlto - recorteAlto)
                        .background(
                            pokemon.tipo.color.copy(alpha = 0.3f),
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp
                            )
                        )
                )
            }
        }
    }
}
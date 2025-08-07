package com.amrubio27.mypokemoncard.pokemonCard.simplified

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCard.domain.Pokemon

@Composable
fun CapaUISimplified(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp
) {
    Column(
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Red.copy(0.5f)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DevState(
                devState = "JUNIOR",
                cartaAncho = cartaAncho
            )

            Text(
                text = pokemon.nombre,
                fontSize = (cartaAncho.value / 18).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.offset(y = 3.dp, x = 2.dp),
                text = "HP",
                fontSize = (cartaAncho.value / 28).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = pokemon.hp.toString(),
                fontSize = (cartaAncho.value / 14).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(4.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_android),
                contentDescription = "Fondo de carta",
                modifier = Modifier
                    .size(24.dp),
                colorFilter = ColorFilter.tint(
                    Color(0xFFFFFFFF)
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red.copy(0.5f))
                .padding(horizontal = 12.dp)
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,

                ) {
                repeat(pokemon.id % 5 + 4) { // Simulamos rareza con el ID
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size((cartaAncho.value / 19).dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                }
            }

            // ReglaEx
            Text(
                text = pokemon.descripcion,
                fontSize = (cartaAncho.value / 30).sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .border(2.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun DevState(devState: String, cartaAncho: Dp) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFF333333)),
                    start = Offset(130f, 0f),
                    end = Offset(150f, 50f)
                )
            )
            .padding(2.dp)
    ) {
        Text(
            text = devState,
            fontSize = (cartaAncho.value / 40).sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Black,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFFFFFFF))
                .height(16.dp)
                .absoluteOffset(y = (-4).dp)
                .padding(horizontal = 6.dp)
        )
    }

}

@Composable
fun BoxTextPersonal(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFF333333)),
                    start = Offset(130f, 0f),
                    end = Offset(150f, 50f)
                )
            )
            .padding(2.dp)
    ) {
        content()
    }
}



package com.amrubio27.mypokemoncard.pokemonCard.simplified

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCard.components.DevState
import com.amrubio27.mypokemoncard.pokemonCard.components.RuleEx
import com.amrubio27.mypokemoncard.pokemonCard.domain.Pokemon

@Composable
fun CapaUISimplified(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp
) {
    Column(
        modifier = modifier.padding(vertical = 12.dp, horizontal = 6.dp)
    ) {
        //Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Red.copy(0.5f)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DevState(
                devState = "JUNIOR", cartaAncho = cartaAncho
            )

            Text(
                text = pokemon.nombre,
                fontSize = (cartaAncho.value / 18).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
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
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(
                    Color(0xFFFFFFFF)
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

        }

        Spacer(modifier = Modifier.weight(1f))

        //Footer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red.copy(0.5f))
                .padding(start = 12.dp)
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //Rarity Icons
            repeat(4) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_outline_mobile_code_24),
                    contentDescription = "Card Rarity",
                    modifier = Modifier.size((cartaAncho.value / 15).dp),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            //RuleEx
            RuleEx {
                Text(
                    text = pokemon.descripcion,
                    fontSize = (cartaAncho.value / 30).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color(0xFFFFFFFF))
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}
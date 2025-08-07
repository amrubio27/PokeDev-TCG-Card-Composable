package com.amrubio27.mypokemoncard.pokemonCardNew.layers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.amrubio27.mypokemoncard.pokemonCardNew.domain.Pokemon
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.components.AttackUI
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.components.DevState
import com.amrubio27.mypokemoncard.pokemonCardNew.layers.components.RuleEx

@Composable
fun UISimplifiedLayer(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp
) {
    Column(
        modifier = modifier.padding(vertical = 12.dp, horizontal = 6.dp)
    ) {
        //Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Red.copy(0.5f))
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DevState(
                devState = pokemon.nivel, cartaAncho = cartaAncho
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
                contentDescription = "Up Right Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(
                    Color(0xFFFFFFFF)
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

        }

        Spacer(modifier = Modifier.weight(1f))

        //Attacks
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Yellow.copy(alpha = 0.5f))
                .padding(horizontal = 2.dp, vertical = 4.dp),
        ) {
            pokemon.ataques.forEach { ataque ->
                AttackUI(
                    ataque = ataque, textSize = (cartaAncho.value / 25).sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Blue.copy(alpha = 0.5f))
                .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height((cartaAncho.value / 17).dp)
                    .padding(horizontal = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(
                    2.dp, MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.1f,
                    )
                )
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Debilidad",
                        fontSize = (cartaAncho.value / 35).sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .offset(y = -2.dp)
                            .padding(end = 2.dp)
                    )
                    Text(
                        text = pokemon.debilidad,
                        fontWeight = FontWeight.Bold,
                        fontSize = (cartaAncho.value / 36).sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.offset(y = -2.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
                    .height((cartaAncho.value / 17).dp),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(
                    2.dp, MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.1f,
                    )
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {

                    Text(
                        text = "Fuerte",
                        fontSize = (cartaAncho.value / 35).sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .offset(y = -2.dp)
                            .padding(end = 2.dp)
                    )
                    Text(
                        text = pokemon.resistencia,
                        fontSize = (cartaAncho.value / 35).sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.offset(y = -2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        //Footer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Red.copy(0.5f))
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
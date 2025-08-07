package com.amrubio27.mypokemoncard.pokemonCardOld.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amrubio27.mypokemoncard.R
import com.amrubio27.mypokemoncard.pokemonCardOld.domain.Pokemon

@Composable
fun CapaUIDatos(
    pokemon: Pokemon, modifier: Modifier = Modifier, cartaAncho: Dp
) {

    Column(
        modifier = modifier.padding(vertical = 12.dp, horizontal = 6.dp),
    ) {
        // Información superior
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            // Card para "BASIC"
            Text(
                text = "JUNIOR",
                fontSize = (cartaAncho.value / 40).sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .height(16.dp)
                    .absoluteOffset(y = (-4).dp)
                    .padding(horizontal = 6.dp)
            )

            Text(
                text = pokemon.nombre,
                fontSize = (cartaAncho.value / 16).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))


            // Grupo de HP
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.offset(x = -12.dp) // Ajuste para que no se superponga con el marco
            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.offset(y = (2).dp),
                        text = "HP",
                        fontSize = (cartaAncho.value / 30).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Column {
                    Text(
                        text = pokemon.hp.toString(),
                        fontSize = (cartaAncho.value / 15).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            //en vez de un icono una imagen
            Image(
                painter = painterResource(id = R.drawable.ic_android),
                contentDescription = "Fondo de carta",
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -10.dp),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    Color(0xFFFFFFFF)
                ) // Tint para que se vea bien
            )
        }
    }

    // Área central con Pokémon (recorte para ver el fondo)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp, end = 0.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {


            // Área central con ataques (después del área del Pokémon)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp, bottom = 8.dp
                    ) // Reducido el padding ya que tenemos la ilustración
            ) {
                // Ataques
                pokemon.ataques.forEach { ataque ->
                    AtaqueUI(
                        ataque = ataque, textSize = (cartaAncho.value / 25).sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Información inferior pegada abajo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Debilidad y resistencia en una Card
                if (pokemon.debilidad != null || pokemon.resistencia != null) {
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
                            2.dp, Color.Gray.copy(alpha = 0.2f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                pokemon.debilidad?.let { debilidad ->
                                    Text(
                                        text = "Debilidad",
                                        fontSize = (cartaAncho.value / 35).sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(y = -2.dp)
                                            .padding(end = 4.dp)
                                    )
                                    Text(
                                        text = "Lunes/Presencial",
                                        fontSize = (cartaAncho.value / 36).sp,
                                        color = debilidad.color,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.offset(y = -2.dp)
                                    )
                                }

                            }

                            pokemon.resistencia?.let { resistencia ->
                                if (pokemon.debilidad != null) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Resistencia",
                                        fontSize = (cartaAncho.value / 55).sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(y = -2.dp)
                                            .padding(end = 8.dp)
                                    )
                                    Text(
                                        text = resistencia.nombreEs,
                                        fontSize = (cartaAncho.value / 52).sp,
                                        color = resistencia.color,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.offset(y = -2.dp)
                                    )
                                }

                            }
                        }
                    }
                }

                // Costo de retirada en una Card separada
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
                        2.dp, Color.Gray.copy(alpha = 0.2f)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
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
                                text = "Curiosidad/TeamWork", //text = pokemon.costoRetirada.toString()
                                fontSize = (cartaAncho.value / 35).sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.offset(y = -2.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rareza (número de iconos de rareza)
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

                // ReglaEx si la hubiera
                val descripcionTexto = pokemon.descripcion.ifEmpty {
                    "Sin descripción"
                }
                Text(
                    text = descripcionTexto,
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
        }
    }
}
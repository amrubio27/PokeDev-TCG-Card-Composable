package com.amrubio27.mypokemoncard.old.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.amrubio27.mypokemoncard.old.domain.Ataque

@Composable
 fun AtaqueUI(
    ataque: Ataque, textSize: TextUnit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            ataque.costo.forEach { tipo ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(tipo.color, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = ataque.nombre,
                fontSize = textSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = ataque.danio.toString(),
                fontSize = textSize * 1.3,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        if (ataque.descripcion.isNotEmpty()) {
            Text(
                text = ataque.descripcion,
                fontSize = textSize * 0.8f,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}
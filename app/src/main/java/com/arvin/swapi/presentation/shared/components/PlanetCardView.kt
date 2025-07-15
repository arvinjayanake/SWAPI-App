package com.arvin.swapi.presentation.shared.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.model.getThumbnailImgUrl

/**
 * Displays a card representing a planet, including its image, name, and climate/orbital details.
 *
 * The card is clickable and triggers [onClick] with the [planet] when pressed.
 *
 * @param planet The [Planet] data to display.
 * @param imgHeight The desired height of the planet image.
 * @param onClick Callback invoked when the card is clicked, passing the [planet].
 */
@Composable
fun PlanetCardView(planet: Planet, imgHeight: Dp, onClick: (Planet) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(planet) },
            horizontalAlignment = Alignment.Start
        ) {
            PlanetImageView(
                imgUrl = planet.getThumbnailImgUrl(),
                height = imgHeight
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = planet.name ?: "-",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "This planet has a ${planet.climate ?: "-"} climate and completes an orbit in ${planet.orbitalPeriod ?: "-"} days.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}
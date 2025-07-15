package com.arvin.swapi.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.arvin.swapi.R

/**
 * Displays a planet image loaded asynchronously from a URL, with rounded corners and a loading indicator.
 *
 * Shows a circular progress indicator while the image is loading, and a default image if loading fails.
 *
 * @param imgUrl The URL of the image to display.
 * @param height The height of the image container (default is 250.dp).
 * @param imageCornerRadius The corner radius for the image container (default is 16.dp).
 */
@Composable
fun PlanetImageView(imgUrl: String?, height: Dp = 250.dp, imageCornerRadius: Dp = 16.dp) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .error(R.drawable.ic_default_img)
            .crossfade(true)
            .crossfade(400)
            .build()
    )
    val state = painter.state

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(topStart = imageCornerRadius, topEnd = imageCornerRadius))
    ) {
        Image(
            painter = painter,
            contentDescription = "Planet Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(imageCornerRadius))
        )

        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp),
                strokeWidth = 2.dp
            )
        }
    }
}
package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileImage(
    url: String, width: Dp = 36.dp, height: Dp = 36.dp,
    colorFilter: ColorFilter? = null
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .size(width, height)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.FillBounds,
        colorFilter = colorFilter

    )
}
package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.luckyfrog.quickmart.utils.resource.theme.colorCyan

@Composable
fun PagerIndicator(
    arraySize: Int,
    currentIndex: Int
) {
    Box(
        modifier = Modifier
            .wrapContentWidth() // Make the width flexible based on content
            .height(16.dp)
            .clip(RoundedCornerShape(12.dp))

            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp), // Add horizontal padding inside the Box

        contentAlignment = Alignment.Center // Center content inside the Box
    ) {
        LazyRow(
            modifier = Modifier
                .wrapContentWidth(), // Wrap content width for LazyRow
            horizontalArrangement = Arrangement.spacedBy(5.dp), // Adds 5dp space between items
            verticalAlignment = Alignment.CenterVertically // Center items vertically
        ) {
            items(arraySize) { index ->
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .height(6.dp)
                        .background(
                            color = if (index == currentIndex) colorCyan else MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape
                        ),
                )
            }
        }
    }
}
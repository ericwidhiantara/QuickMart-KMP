package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.luckyfrog.quickmart.utils.resource.theme.colorCyan
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CarouselWithOverlay(items: List<CarouselData>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val currentIndex = remember { mutableStateOf(0) }

    // Auto-scroll effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)  // Delay between scrolls (3 seconds in this case)
            currentIndex.value = (currentIndex.value + 1) % items.size
            coroutineScope.launch {
                listState.animateScrollToItem(index = currentIndex.value)
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, // Center item horizontally without spacing
        contentPadding = PaddingValues(0.dp) // No padding on the LazyRow
    ) {
        items(items.size) { index ->
            CarouselItemWithOverlay(
                item = items[index],
                arraySize = items.size,
                currentIndex = index
            )
        }
    }
}

@Composable
fun CarouselItemWithOverlay(item: CarouselData, arraySize: Int, currentIndex: Int) {
    Box(
        modifier = Modifier
            .size(360.dp, 148.dp)
            .background(Color.Gray, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp)) // Clip content to ensure rounded corners
    ) {
        // Background Image
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            // Overlay Text
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "30% OFF",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.subtitle,
                    color = Color.White,
                    fontSize = 12.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    // Pager Indicator
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
            }


        }

    }
}

data class CarouselData(
    val imageUrl: String,
    val title: String,
    val subtitle: String
)

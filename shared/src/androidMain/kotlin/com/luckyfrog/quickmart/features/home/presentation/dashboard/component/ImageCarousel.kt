package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.luckyfrog.quickmart.core.widgets.PagerIndicator
import kotlinx.coroutines.delay

data class CarouselData(
    val imageUrl: String,
    val title: String,
    val subtitle: String
)

@Composable
fun Carousel(modifier: Modifier = Modifier) {
    val items = listOf(
        CarouselData(
            imageUrl = "https://via.placeholder.com/348x148",
            title = "On Headphones",
            subtitle = "Exclusive Sales"
        ),
        CarouselData(
            imageUrl = "https://via.placeholder.com/348x148",
            title = "Title 2",
            subtitle = "Subtitle 2"
        ),
        CarouselData(
            imageUrl = "https://via.placeholder.com/348x148",
            title = "Title 3",
            subtitle = "Subtitle 3"
        )
    )

    val pagerState = rememberPagerState(
        pageCount =
        { items.size }
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(
        modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier

        ) {
            HorizontalPager(
                state = pagerState,
                modifier
                    .fillMaxWidth()
            ) { currentPage ->

                CarouselItemWithOverlay(
                    item = items[currentPage],
                    arraySize = items.size,
                    currentIndex = currentPage
                )
            }
        }

    }
}


@Composable
fun CarouselItemWithOverlay(item: CarouselData, arraySize: Int, currentIndex: Int) {
    // Get the screen height from LocalConfiguration
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .width(screenWidth)
            .height(148.dp) // Fixed height
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
                    PagerIndicator(arraySize = arraySize, currentIndex = currentIndex)

                }
            }

        }

    }
}

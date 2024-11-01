package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryListViewModel
import com.luckyfrog.quickmart.features.category.presentation.categories.component.CategoryCard
import com.luckyfrog.quickmart.utils.PageLoader

@Composable
fun CategoryList(
    viewModel: CategoryListViewModel = hiltViewModel(),
) {

    // Trigger the category fetch
    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    // Observe the category from the ViewModel
    val data by viewModel.data.collectAsState()
    when {
        data == null -> {
            // Loading or error state
            // Loading or error state with centered CircularProgressIndicator
            PageLoader(modifier = Modifier.height(60.dp))
        }

        else -> {

            // Category is loaded, show its details
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                val itemCount = data?.size ?: 0
                val displayCount =
                    itemCount.coerceAtMost(4)

                items(displayCount) { index ->
                    CategoryCard(
                        itemEntity = data!![index],
                        onClick = {
                        }
                    )
                }
                item { Spacer(modifier = Modifier.padding(4.dp)) }
            }
        }
    }

}


@Composable
fun CategoryItem(
    category: CategoryEntity,
    onClick: () -> Unit
) {
    TextButton(
        modifier = Modifier
            .clip(
                RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        content = {

            Text(
                text = category.name ?: "-",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )

}

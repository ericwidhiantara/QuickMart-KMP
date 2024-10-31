package com.luckyfrog.quickmart.features.home.presentation.dashboard.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryList() {
    val categories = listOf(
        Category("📱", "Electronics"),
        Category("👜", "Fashion"),
        Category("🛋️", "Furniture"),
        Category("🚗", "Industrial")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.forEach { category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
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
        onClick = {

        },
        content = {
            Text(
                text = category.label,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )

}

data class Category(val icon: String, val label: String)

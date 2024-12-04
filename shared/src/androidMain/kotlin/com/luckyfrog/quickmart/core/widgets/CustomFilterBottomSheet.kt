package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.utils.resource.theme.borderColor

@Composable
fun CustomFilterBottomSheet(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onApply: () -> Unit
) {
    // Checkbox options
    val filters = listOf(
        stringResource(R.string.filter_price_low_to_high),
        stringResource(R.string.filter_price_high_to_low),
        stringResource(R.string.filter_a_to_z),
        stringResource(R.string.filter_z_to_a),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                MaterialTheme.colorScheme.background
            ),
    ) {
        // Header
        Text(
            text = stringResource(R.string.filter),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )

        filters.forEach { filter ->
            Column(modifier = Modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.background(Color.Transparent),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        ),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(
                            1.5.dp,
                            color = MaterialTheme.colorScheme.borderColor
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(if (selectedFilter == filter) MaterialTheme.colorScheme.primary else Color.White)
                                .clickable {
                                    onFilterSelected(filter)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedFilter == filter)
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = filter,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                HorizontalDivider()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Apply button
        CustomOutlinedButton(
            buttonText = stringResource(R.string.apply),
            onClick = onApply
        )
        Spacer(modifier = Modifier.height(24.dp))
        
    }
}

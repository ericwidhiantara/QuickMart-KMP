package com.luckyfrog.quickmart.features.cart.presentation.my_cart.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.utils.resource.theme.borderColor
import com.luckyfrog.quickmart.utils.resource.theme.colorRed

@Composable
fun CartItemCard(
    imageUrl: String,
    productName: String,
    currentPrice: Double,
    originalPrice: Double,
    quantity: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = productName,
            modifier = Modifier
                .size(120.dp, 120.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 12.dp)
                .weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = productName,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                )
            }

            Text(
                text = String.format("$%.2f", currentPrice),
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = String.format("$%.2f", originalPrice),
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                textDecoration = TextDecoration.LineThrough
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuantitySelector(
                    quantity = quantity,
                    onQuantityChanged = onQuantityChange,
                    modifier = Modifier
                        .border(
                            border = BorderStroke(
                                1.dp,
                                color = MaterialTheme.colorScheme.borderColor,
                            ),
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(10.dp)
                )
                IconButton(onClick = onDelete) {
                    Image(
                        painter = painterResource(id = Images.icDeleteCart),
                        contentDescription = "delete cart",
                        colorFilter = ColorFilter.tint(colorRed),
                    )
                }
            }
        }
    }
}
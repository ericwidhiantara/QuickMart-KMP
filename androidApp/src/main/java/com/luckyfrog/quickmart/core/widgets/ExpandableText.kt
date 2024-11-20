package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckyfrog.quickmart.R

@Composable
fun ExpandableText(
    text: String,
    maxLines: Int = 3,
    fontSize: TextUnit = 14.sp
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showMoreButton by remember { mutableStateOf(false) }

    Column {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            onTextLayout = { textLayoutResult ->
                // Only update if the button isn't already shown
                if (!showMoreButton) {
                    showMoreButton = textLayoutResult.hasVisualOverflow
                }
            }
        )

        if (showMoreButton) {
            TextButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = stringResource(id = if (isExpanded) R.string.show_less else R.string.read_more),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = fontSize
                )
            }
        }
    }
}
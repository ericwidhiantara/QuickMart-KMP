package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.core.resources.Images

@Composable
fun CustomTopBar(
    title: String,
    navController: NavController,
    centeredTitle: Boolean = true,
    actions: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp), // Padding for spacing
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (centeredTitle && navController.previousBackStackEntry == null && actions == null) {
                Arrangement.Center
            } else {
                Arrangement.SpaceBetween
            }
        ) {
            // Left side with back button (only if available) and title
            if (navController.previousBackStackEntry != null || !centeredTitle) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = Images.icArrowBack),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = if (navController.previousBackStackEntry != null) 8.dp else 0.dp), // Space if back button is present
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                // Center the title if no back button or actions and centeredTitle is true
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Right side actions (only if provided)
            if (actions != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    actions()
                }
            }
        }
        HorizontalDivider()
    }
}

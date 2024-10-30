package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
    actions: @Composable () -> Unit = {}
) {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Automatically show back button if there's a backstack entry
            if (navController.previousBackStackEntry != null) {
                IconButton(
                    onClick = { navController.popBackStack() }, // Navigate back
                    modifier = Modifier
                        .padding(16.dp)
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
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                actions()
            }
        }
        HorizontalDivider()
    }
}

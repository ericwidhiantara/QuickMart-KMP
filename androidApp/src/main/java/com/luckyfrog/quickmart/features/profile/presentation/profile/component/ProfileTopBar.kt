package com.luckyfrog.quickmart.features.profile.presentation.profile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserState

@Composable
fun ProfileTopBar(
    userState: UserState,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (userState) {
        is UserState.Success -> {
            ProfileTopBarContent(
                fullName = userState.data.fullname,
                email = userState.data.email,
                onLogoutClick = onLogoutClick,
                modifier = modifier
            )
        }

        is UserState.Error, is UserState.Idle -> {
            ProfileTopBarContent(
                fullName = null,
                email = null,
                onLogoutClick = onLogoutClick,
                modifier = modifier
            )
        }

        is UserState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(21.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
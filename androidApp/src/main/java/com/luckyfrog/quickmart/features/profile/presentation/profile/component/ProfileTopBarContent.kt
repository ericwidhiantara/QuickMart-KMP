package com.luckyfrog.quickmart.features.profile.presentation.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.ProfileImage


@Composable
fun ProfileTopBarContent(
    fullName: String?,
    email: String?,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 21.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileImage(
                width = 40.dp,
                height = 40.dp,
                url = "https://avatar.iran.liara.run/public/66",
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = fullName ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = email ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onLogoutClick) {
                Image(
                    painter = painterResource(Images.icLogout),
                    contentDescription = stringResource(R.string.logout),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}
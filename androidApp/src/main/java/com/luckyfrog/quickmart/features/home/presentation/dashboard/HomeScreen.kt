package com.luckyfrog.quickmart.features.home.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.features.auth.presentation.login.UserState
import com.luckyfrog.quickmart.features.auth.presentation.login.UserViewModel
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.CarouselData
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.CarouselWithOverlay
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.CategoryList
import com.luckyfrog.quickmart.features.home.presentation.dashboard.component.ProfileImage
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import java.util.Locale

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    userViewModel: UserViewModel
) {
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

    val userState by userViewModel.userState.collectAsState()

    // Trigger the get user fetch
    LaunchedEffect(Unit) {
        userViewModel.getUserLogin()
    }

    Scaffold(
        modifier = Modifier.padding(vertical = 10.dp),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        when (mainViewModel.stateApp.theme) {
                            AppTheme.Light -> Images.icLogoDark
                            AppTheme.Dark -> Images.icLogoLight
                            AppTheme.Default -> if (isSystemInDarkTheme()) Images.icLogoLight else Images.icLogoDark
                        }
                    ),
                    modifier = Modifier.height(32.dp),
                    contentDescription = "Logo"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                painter = painterResource(Images.icSearch),
                                contentDescription = "Search",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            modifier = Modifier.width(42.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            shape = RoundedCornerShape(8.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            when (val state = userState) {
                                is UserState.Success -> {
                                    ProfileImage(
                                        state.data.image ?: "https://via.placeholder.com/48x48"
                                    )
                                }

                                is UserState.Error, is UserState.Idle -> {
                                    ProfileImage("https://via.placeholder.com/48x48")
                                }

                                is UserState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                }

                                else -> {
                                    ProfileImage("https://via.placeholder.com/48x48")
                                }
                            }
                        }

                    }
                )

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CarouselWithOverlay(items = items)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.categories),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                TextButton(
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(R.string.see_all).uppercase(Locale.getDefault()),
                        fontSize = 12.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            CategoryList()
        }
    }
}
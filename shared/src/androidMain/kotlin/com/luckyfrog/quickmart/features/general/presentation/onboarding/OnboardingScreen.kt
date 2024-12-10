package com.luckyfrog.quickmart.features.general.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.AppPreferences
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import com.luckyfrog.quickmart.utils.resource.theme.colorCyan
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    navController: NavController
) {
    // Get the screen height from LocalConfiguration
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val currentIndex = remember { mutableIntStateOf(0) }
    val titles = listOf(
        R.string.onboarding_title_1,
        R.string.onboarding_title_2,
        R.string.onboarding_title_3
    )
    val arraySize = titles.size

    val subtitles = listOf(
        R.string.onboarding_subtitle_1,
        R.string.onboarding_subtitle_2,
        R.string.onboarding_subtitle_3
    )

    val images = listOf(
        Images.icOnboarding1,
        Images.icOnboarding2,
        Images.icOnboarding3
    )
    val context = LocalContext.current


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.52f)
                    .clip(
                        RoundedCornerShape(32.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        content = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (currentIndex.intValue != 0)
                                    IconButton(
                                        onClick = {
                                            currentIndex.intValue -= 1
                                        }, // Navigate back
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = stringResource(id = Images.icArrowBack),
                                            tint = MaterialTheme.colorScheme.onSecondary,
                                            modifier = Modifier
                                                .height(32.dp)
                                                .width(32.dp)
                                        )
                                    }
                                else
                                    Image(
                                        modifier = Modifier
                                            .height(32.dp)
                                            .width(104.dp),
                                        painter = painterResource(
                                            when (mainViewModel.stateApp.theme) {
                                                AppTheme.Light -> Images.icLogoDark
                                                AppTheme.Dark -> Images.icLogoLight
                                                AppTheme.Default -> if (isSystemInDarkTheme()) Images.icLogoLight else Images.icLogoDark
                                            }
                                        ),
                                        contentDescription = "Logo"
                                    )

                                if (currentIndex.intValue < arraySize - 1)
                                    TextButton(onClick = {
                                        AppPreferences.setFirstTime(context)
                                        navController.navigate(AppScreen.RegisterScreen.route) {
                                            popUpTo(AppScreen.OnboardingScreen.route) {
                                                inclusive = true
                                            }
                                        }
                                    }) {
                                        Text(
                                            text = stringResource(R.string.skip_for_now),
                                            color = colorCyan
                                        )
                                    }

                            }
                            Spacer(modifier = Modifier.height(51.dp))
                            Image(
                                painter = painterResource(
                                    images[currentIndex.intValue]
                                ),

                                contentDescription = "Onboarding",
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .width(240.dp)
                                    .height(240.dp),

                                )
                            Spacer(modifier = Modifier.height(70.dp))

                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(titles[currentIndex.intValue]),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(subtitles[currentIndex.intValue]),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (currentIndex.intValue == arraySize - 1)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        CustomOutlinedButton(
                            buttonText = stringResource(R.string.login),
                            buttonTextColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.width(screenWidth * 0.45F),
                            onClick = {
                                if (currentIndex.intValue == arraySize - 1) {
                                    AppPreferences.setFirstTime(context)

                                    navController.navigate(AppScreen.LoginScreen.route) {
                                        popUpTo(AppScreen.OnboardingScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    currentIndex.intValue++
                                }

                            },
                            buttonBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            buttonContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else Color.Transparent,

                            )
                        CustomOutlinedButton(
                            buttonText = stringResource(R.string.get_started),
                            modifier = Modifier.width(screenWidth * 0.45F),
                            isWithIcon = true,
                            buttonIcon = painterResource(Images.icArrowForward),
                            onClick = {
                                if (currentIndex.intValue == arraySize - 1) {
                                    AppPreferences.setFirstTime(context)

                                    navController.navigate(AppScreen.RegisterScreen.route) {
                                        popUpTo(AppScreen.OnboardingScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    currentIndex.intValue++
                                }

                            },
                            buttonContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer,
                        )

                    }
                )
            else
                CustomOutlinedButton(
                    buttonText = stringResource(R.string.next),
                    onClick = {
                        if (currentIndex.intValue == arraySize - 1) {
                            AppPreferences.setFirstTime(context)
                            navController.navigate(AppScreen.LoginScreen.route) {
                                popUpTo(AppScreen.OnboardingScreen.route) { inclusive = true }
                            }
                        } else {
                            currentIndex.intValue++
                        }
                    },
                    buttonContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer,
                )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .width(66.dp)
                    .height(26.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.CenterHorizontally), // Align Box in the center of its parent
                contentAlignment = Alignment.Center // Ensures the content inside the Box is centered
            ) {
                LazyRow(
                    modifier = Modifier
                        .wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp), // Adds 5dp space between items
                    verticalAlignment = Alignment.CenterVertically // Center items vertically
                ) {
                    items(arraySize) { index ->
                        Box(
                            modifier = Modifier
                                .width(10.dp)
                                .height(10.dp)
                                .background(
                                    color = if (index == currentIndex.intValue) colorCyan else MaterialTheme.colorScheme.onBackground,
                                    shape = CircleShape
                                ),
                        )
                    }
                }
            }
        }
    }
}
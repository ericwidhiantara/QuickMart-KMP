package com.luckyfrog.quickmart.features.profile.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.ConfirmationDialog
import com.luckyfrog.quickmart.features.profile.presentation.profile.component.ProfileTopBar
import com.luckyfrog.quickmart.utils.TokenManager
import com.luckyfrog.quickmart.utils.resource.route.AppScreen

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    tokenManager: TokenManager = TokenManager(LocalContext.current),
) {
    val userState by userViewModel.userState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val settingsSections = listOf(
        SettingsSection(
            title = stringResource(id = R.string.personal_information),
            items = listOf(
                SettingsItem(
                    title = stringResource(id = R.string.shipping_address),
                    icon = Images.icShipping,
                    onClick = {
                        navController.navigate(AppScreen.ShippingAddressScreen.route)
                    }
                ),
                SettingsItem(
                    title = stringResource(id = R.string.order_history),
                    icon = Images.icOrderHistory,
                    onClick = {}
                ),
            )
        ),
        SettingsSection(
            title = stringResource(id = R.string.support_and_information),
            items = listOf(
                SettingsItem(
                    title = stringResource(id = R.string.privacy_policy),
                    icon = Images.icPrivacyPolicy,
                    onClick = {}
                ),
                SettingsItem(
                    title = stringResource(id = R.string.terms_and_conditions),
                    icon = Images.icTermsAndConditions,
                    onClick = {}
                ),
                SettingsItem(
                    title = stringResource(id = R.string.faqs),
                    icon = Images.icFaq,
                    onClick = {}
                ),
            )
        ),
        SettingsSection(
            title = stringResource(id = R.string.account_management),
            items = listOf(
                SettingsItem(
                    title = stringResource(id = R.string.change_password),
                    icon = Images.icChangePassword,
                    onClick = {
                        navController.navigate(AppScreen.CheckPasswordScreen.route)
                    }
                ),
                SettingsItem(
                    title = stringResource(id = R.string.theme),
                    icon = Images.icTheme,
                    onClick = {}
                ),
                SettingsItem(
                    title = stringResource(id = R.string.language),
                    icon = Images.icLanguage,
                    onClick = {}
                ),
            )
        ),
    )

    LaunchedEffect(Unit) {
        userViewModel.getUserLogin()
    }

    Scaffold(
        topBar = {
            ProfileTopBar(
                userState = userState,
                onLogoutClick = { showLogoutDialog = true }
            )
            if (showLogoutDialog) {
                ConfirmationDialog(
                    title = stringResource(id = R.string.confirm_logout),
                    description = stringResource(id = R.string.confirm_logout_desc),
                    confirmText = stringResource(id = R.string.logout),
                    onConfirm = {
                        showLogoutDialog = false
                        // Add logout logic here
                        tokenManager.clearTokens()
                        navController.navigate(AppScreen.LoginScreen.route) {
                            popUpTo(AppScreen.MainScreen.route) {
                                inclusive = true
                            }  // Clear back stack
                        }
                    },
                    onDismiss = {
                        showLogoutDialog = false
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            settingsSections.forEach { section ->
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = section.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(section.items.size) { index ->
                    val item = section.items[index]
                    Button(
                        onClick = { item.onClick() },
                        modifier = Modifier
                            .padding(10.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground

                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(item.icon),
                                contentDescription = item.title,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = item.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

class SettingsSection(
    val title: String,
    val items: List<SettingsItem>
)

class SettingsItem(
    val title: String,
    val icon: Int,
    val onClick: () -> Unit
)
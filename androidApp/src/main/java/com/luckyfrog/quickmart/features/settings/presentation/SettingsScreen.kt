package com.luckyfrog.quickmart.features.settings.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.auth.presentation.login.UserState
import com.luckyfrog.quickmart.features.auth.presentation.login.UserViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError

@Composable
fun SettingsScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {
    val userState by userViewModel.userState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.product_detail),
                navController = navController
            )
        },
        content = { paddingValues ->
            // Trigger the get user fetch
            LaunchedEffect(Unit) {
                userViewModel.getUserLogin()
            }

            // Display the user details
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {

                when (val state = userState) {

                    is UserState.Success -> {
                        Text(text = state.data.username ?: "")

                    }

                    is UserState.Error -> {
                        // Show a toast with the error message
                        // on below line we are displaying a custom toast message on below line
                        SweetError(
                            message = state.message,
                            duration = Toast.LENGTH_SHORT,
                            padding = PaddingValues(top = 16.dp),
                            contentAlignment = Alignment.TopCenter
                        )
                        Log.d("LoginScreen", "Error State: $state")

                    }

                    is UserState.Loading -> {
                        // Show a loading spinner or something similar if you want
                    }

                    is UserState.Idle -> {
                        // No action yet
                    }


                    else -> {}
                }

            }
        }
    )

}
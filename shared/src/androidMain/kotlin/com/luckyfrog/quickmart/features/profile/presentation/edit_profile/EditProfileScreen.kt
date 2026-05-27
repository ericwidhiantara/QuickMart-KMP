package com.luckyfrog.quickmart.features.profile.presentation.edit_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.features.auth.domain.entities.UserEntity
import com.luckyfrog.quickmart.features.profile.domain.entities.UpdateProfileParamsEntity
import com.luckyfrog.quickmart.features.profile.presentation.profile.UpdateProfileState
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserState
import com.luckyfrog.quickmart.features.profile.presentation.profile.UserViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: UserViewModel = koinViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val updateState by viewModel.updateProfileState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var populated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.getUserLogin() }

    LaunchedEffect(userState) {
        if (!populated && userState is UserState.Success) {
            val u = (userState as UserState.Success).data
            fullname = u.fullname ?: ""
            username = u.username ?: ""
            email = u.email ?: ""
            phoneNumber = u.phoneNumber ?: ""
            gender = u.gender ?: ""
            birthDate = u.birthDate ?: ""
            populated = true
        }
    }

    LaunchedEffect(updateState) {
        when (updateState) {
            is UpdateProfileState.Success -> {
                viewModel.resetUpdateProfileState()
                navController.popBackStack()
            }
            is UpdateProfileState.Error -> {
                snackbarHostState.showSnackbar((updateState as UpdateProfileState.Error).message)
                viewModel.resetUpdateProfileState()
            }
            else -> {}
        }
    }

    val isLoading = updateState is UpdateProfileState.Loading

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileField("Full name", fullname) { fullname = it }
            ProfileField("Username", username) { username = it }
            ProfileField("Email", email) { email = it }
            ProfileField("Phone number", phoneNumber) { phoneNumber = it }
            ProfileField("Gender (male/female)", gender) { gender = it }
            ProfileField("Birth date (DD-MM-YYYY)", birthDate) { birthDate = it }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.updateProfile(
                        UpdateProfileParamsEntity(
                            fullname = fullname.ifBlank { null },
                            username = username.ifBlank { null },
                            email = email.ifBlank { null },
                            phoneNumber = phoneNumber.ifBlank { null },
                            gender = gender.ifBlank { null },
                            birthDate = birthDate.ifBlank { null }
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.height(20.dp), strokeWidth = 2.dp)
                else Text("Save Changes", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

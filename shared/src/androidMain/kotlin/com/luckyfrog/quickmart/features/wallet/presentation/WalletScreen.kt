package com.luckyfrog.quickmart.features.wallet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    navController: NavController,
    viewModel: WalletViewModel = koinViewModel()
) {
    val walletState by viewModel.walletState.collectAsState()
    val topUpState by viewModel.topUpState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var amount by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.fetchWallet() }

    LaunchedEffect(topUpState) {
        when (topUpState) {
            is TopUpState.Success -> {
                snackbarHostState.showSnackbar("Top-up successful!")
                viewModel.resetTopUpState()
                viewModel.fetchWallet()
                amount = ""
            }
            is TopUpState.Error -> {
                snackbarHostState.showSnackbar((topUpState as TopUpState.Error).message)
                viewModel.resetTopUpState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Wallet") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Balance card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Current Balance", style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(Modifier.height(8.dp))
                    when (val s = walletState) {
                        is WalletState.Loading -> CircularProgressIndicator()
                        is WalletState.Success -> Text(
                            s.wallet.localizedBalance,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        is WalletState.Error -> Text(s.message, color = MaterialTheme.colorScheme.error)
                        else -> Text("—", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Top-up section
            Text("Top Up", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                prefix = { Text("Rp ") }
            )

            val isLoading = topUpState is TopUpState.Loading
            Button(
                onClick = {
                    val value = amount.toDoubleOrNull()
                    if (value != null && value > 0) viewModel.topUp(value)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && amount.toDoubleOrNull() != null && (amount.toDoubleOrNull() ?: 0.0) > 0
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.height(20.dp), strokeWidth = 2.dp)
                else Text("Top Up", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

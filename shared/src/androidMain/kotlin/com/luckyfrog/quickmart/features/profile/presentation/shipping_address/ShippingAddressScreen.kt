package com.luckyfrog.quickmart.features.profile.presentation.shipping_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.ShippingAddressEntity
import com.luckyfrog.quickmart.features.shipping_address.presentation.address_list.AddressListState
import com.luckyfrog.quickmart.features.shipping_address.presentation.address_list.AddressMutationState
import com.luckyfrog.quickmart.features.shipping_address.presentation.address_list.ShippingAddressListViewModel
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingAddressScreen(
    navController: NavController,
    viewModel: ShippingAddressListViewModel = koinViewModel()
) {
    val listState by viewModel.listState.collectAsState()
    val mutationState by viewModel.mutationState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var addressToDelete by remember { mutableStateOf<ShippingAddressEntity?>(null) }

    LaunchedEffect(Unit) { viewModel.fetchAddresses() }
    LaunchedEffect(mutationState) {
        if (mutationState is AddressMutationState.Deleted || mutationState is AddressMutationState.Success) {
            viewModel.resetMutationState()
        }
    }

    if (showDeleteDialog && addressToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Address") },
            text = { Text("This address will be permanently deleted.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    addressToDelete?.id?.let { viewModel.deleteAddress(it) }
                }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shipping Addresses") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AppScreen.ShippingAddressFormScreen.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add address")
            }
        }
    ) { padding ->
        when (val s = listState) {
            is AddressListState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is AddressListState.Success -> {
                if (s.addresses.isEmpty()) {
                    Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                        Text("No addresses saved. Tap + to add one.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(padding).fillMaxSize()
                    ) {
                        items(s.addresses) { address ->
                            AddressCardItem(
                                address = address,
                                onSetDefault = { address.id?.let { viewModel.setDefault(it) } },
                                onEdit = { navController.navigate(AppScreen.ShippingAddressFormScreen.route + "?isEdit=true&addressId=${address.id}") },
                                onDelete = { addressToDelete = address; showDeleteDialog = true }
                            )
                        }
                    }
                }
            }
            is AddressListState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text(s.message, color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun AddressCardItem(
    address: ShippingAddressEntity,
    onSetDefault: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                    Text(address.label ?: "Address", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    if (address.isDefault) {
                        SuggestionChip(onClick = {}, label = { Text("Default", style = MaterialTheme.typography.labelSmall) })
                    }
                }
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        if (!address.isDefault) {
                            DropdownMenuItem(text = { Text("Set as Default") }, onClick = { showMenu = false; onSetDefault() })
                        }
                        DropdownMenuItem(text = { Text("Edit") }, onClick = { showMenu = false; onEdit() })
                        DropdownMenuItem(text = { Text("Delete", color = MaterialTheme.colorScheme.error) }, onClick = { showMenu = false; onDelete() })
                    }
                }
            }

            Spacer(Modifier.height(6.dp))
            HorizontalDivider()
            Spacer(Modifier.height(6.dp))

            Text(address.recipientName ?: "", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(address.phone ?: "", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                listOfNotNull(address.addressLine, address.city, address.province, address.postalCode, address.country)
                    .filter { it.isNotBlank() }.joinToString(", "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Keep for NavGraph backward compat
@Serializable
data class ShippingItem(
    val recipientName: String,
    val recipientPhone: String,
    val recipientAddress: String,
    val isPrimary: Boolean,
)

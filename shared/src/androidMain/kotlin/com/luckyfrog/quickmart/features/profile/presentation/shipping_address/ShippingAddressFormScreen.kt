package com.luckyfrog.quickmart.features.profile.presentation.shipping_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.CreateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.UpdateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.presentation.address_list.AddressListState
import com.luckyfrog.quickmart.features.shipping_address.presentation.address_list.AddressMutationState
import com.luckyfrog.quickmart.features.shipping_address.presentation.address_list.ShippingAddressListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingAddressFormScreen(
    navController: NavController,
    isEdit: Boolean = false,
    addressId: String? = null,
    viewModel: ShippingAddressListViewModel = koinViewModel()
) {
    val listState by viewModel.listState.collectAsState()
    val mutationState by viewModel.mutationState.collectAsState()

    var label by remember { mutableStateOf("") }
    var recipientName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var addressLine by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("Indonesia") }
    var isDefault by remember { mutableStateOf(false) }
    var fieldsPopulated by remember { mutableStateOf(!isEdit) }

    // In edit mode, fetch addresses then populate fields from matching address
    LaunchedEffect(Unit) {
        if (isEdit && addressId != null) {
            viewModel.fetchAddresses()
        }
    }

    // Populate fields once when list loads in edit mode
    LaunchedEffect(listState) {
        if (isEdit && !fieldsPopulated && listState is AddressListState.Success) {
            val address = (listState as AddressListState.Success).addresses
                .firstOrNull { it.id == addressId }
            if (address != null) {
                label = address.label ?: ""
                recipientName = address.recipientName ?: ""
                phone = address.phone ?: ""
                addressLine = address.addressLine ?: ""
                city = address.city ?: ""
                province = address.province ?: ""
                postalCode = address.postalCode ?: ""
                country = address.country ?: "Indonesia"
                isDefault = address.isDefault
                fieldsPopulated = true
            }
        }
    }

    // Navigate back on success
    LaunchedEffect(mutationState) {
        if (mutationState is AddressMutationState.Success) {
            viewModel.resetMutationState()
            navController.popBackStack()
        }
    }

    val isLoading = mutationState is AddressMutationState.Loading
    val isFormValid = label.isNotBlank() && recipientName.isNotBlank() && phone.isNotBlank() &&
            addressLine.isNotBlank() && city.isNotBlank() && province.isNotBlank() && postalCode.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Edit Address" else "New Address") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isEdit && !fieldsPopulated) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FormField("Label (e.g. Home, Office)", label) { label = it }
                FormField("Full name", recipientName) { recipientName = it }
                FormField("Phone number", phone) { phone = it }
                FormField("Street address", addressLine) { addressLine = it }
                FormField("City", city) { city = it }
                FormField("Province", province) { province = it }
                FormField("Postal code", postalCode) { postalCode = it }
                FormField("Country", country) { country = it }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Set as default address", style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium)
                    Switch(checked = isDefault, onCheckedChange = { isDefault = it })
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (isEdit && addressId != null) {
                            viewModel.updateAddress(
                                UpdateShippingAddressParamsEntity(
                                    id = addressId,
                                    label = label,
                                    recipientName = recipientName,
                                    phone = phone,
                                    addressLine = addressLine,
                                    city = city,
                                    province = province,
                                    postalCode = postalCode,
                                    country = country,
                                    isDefault = isDefault
                                )
                            )
                        } else {
                            viewModel.createAddress(
                                CreateShippingAddressParamsEntity(
                                    label = label,
                                    recipientName = recipientName,
                                    phone = phone,
                                    addressLine = addressLine,
                                    city = city,
                                    province = province,
                                    postalCode = postalCode,
                                    country = country,
                                    isDefault = isDefault
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && isFormValid
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(if (isEdit) "Update Address" else "Add Address",
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun FormField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

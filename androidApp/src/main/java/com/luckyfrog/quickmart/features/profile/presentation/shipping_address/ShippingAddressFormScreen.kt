package com.luckyfrog.quickmart.features.profile.presentation.shipping_address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.validators.DefaultValidator
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.utils.resource.theme.colorRed

@Composable
fun ShippingAddressFormScreen(
    navController: NavController,
    isEdit: Boolean = false,
) {
    val recipientAddressController =
        remember { mutableStateOf("") }
    val recipientNameController =
        remember { mutableStateOf("") }
    val recipientPhoneController =
        remember { mutableStateOf("") }

    var isPrimaryAddress by remember { mutableStateOf(false) }

    var shouldValidate by remember { mutableStateOf(false) }

    val validator = DefaultValidator()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(
                    if (isEdit) R.string.edit_shipping_address else R.string.add_shipping_address
                ),
                navController = navController,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
        ) {

            CustomTextField(
                validator = validator,
                errorMessage = if (recipientAddressController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_length, 2
                ),
                shouldValidate = shouldValidate,
                value = recipientAddressController.value,
                onValueChange = { newText ->
                    recipientAddressController.value = newText
                },
                minLines = 5,
                titleLabel = stringResource(R.string.recipient_address),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.recipient_address_placeholder),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                validator = validator,
                errorMessage = if (recipientNameController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_length, 2
                ),
                shouldValidate = shouldValidate,
                value = recipientNameController.value,
                onValueChange = { newText ->
                    recipientNameController.value = newText
                },
                titleLabel = stringResource(R.string.recipient_name),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.recipient_name_placeholder),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                validator = validator,
                errorMessage = if (recipientPhoneController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_length, 2
                ),
                shouldValidate = shouldValidate,
                value = recipientPhoneController.value,
                onValueChange = { newText ->
                    recipientPhoneController.value = newText
                },
                titleLabel = stringResource(R.string.recipient_phone),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.recipient_phone_placeholder),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.primary_address),
                        fontSize = 14.sp
                    )
                    Text(
                        text = " *",
                        fontSize = 14.sp,
                        color = colorRed
                    )
                }
                Spacer(
                    modifier = Modifier.height(3.dp)
                )
                Switch(checked = isPrimaryAddress, onCheckedChange = {
                    isPrimaryAddress = it
                })

            }
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                isButtonEnabled = true,
                buttonText = stringResource(R.string.save),
                onClick = {
                    shouldValidate = true

                }
            )


        }
    }
}
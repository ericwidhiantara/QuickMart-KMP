package com.luckyfrog.quickmart.features.cart.presentation.my_cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField

@Composable
fun VoucherCodeBottomSheet(
    onApply: (String) -> Unit,
) {
    val voucherController =
        remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                MaterialTheme.colorScheme.background
            ),
    ) {
        Text(
            text = stringResource(id = R.string.voucher_code),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = voucherController.value,
            onValueChange = { newText ->
                voucherController.value = newText
            },
            withTitleLabel = false,
            titleLabelFontSize = 12.sp,
            placeholder = stringResource(R.string.voucher_code_placeholder),
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomOutlinedButton(
            buttonText = stringResource(R.string.apply),
            onClick = { onApply(voucherController.value) }
        )
    }
}
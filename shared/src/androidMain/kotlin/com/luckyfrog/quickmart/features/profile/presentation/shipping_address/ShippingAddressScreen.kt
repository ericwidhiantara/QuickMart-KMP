package com.luckyfrog.quickmart.features.profile.presentation.shipping_address

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.colorWhite
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@Composable
fun ShippingAddressScreen(navController: NavController) {
    val items = listOf(
        ShippingItem(
            recipientName = "John Doe",
            recipientPhone = "123-456-7890",
            recipientAddress = "123 Main St, Anytown USA",
            isPrimary = true
        ),
        ShippingItem(
            recipientName = "Mark",
            recipientPhone = "123-456-7890",
            recipientAddress = "123 Main St, Anytown USA",
            isPrimary = false
        ),
        ShippingItem(
            recipientName = "Emily",
            recipientPhone = "123-456-7890",
            recipientAddress = "123 Main St, Anytown USA",
            isPrimary = false
        )

    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.shipping_address),
                navController = navController,
            )
        },
        bottomBar = {
            CustomOutlinedButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    navController.navigate(AppScreen.ShippingAddressFormScreen.route)
                },
                buttonText = stringResource(R.string.add_shipping_address),
                buttonBorderColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp)

        ) {
            items(items) { item ->
                Button(
                    onClick = {
                        val jsonString = Json.encodeToString(item)
                        val encodedJson = URLEncoder.encode(jsonString, "UTF-8")
                        navController.navigate(AppScreen.ShippingAddressFormScreen.route + "?isEdit=true&item=$encodedJson")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        ),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (item.isPrimary) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            if (item.isPrimary) {
                                Box(
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(
                                                8.dp
                                            )
                                        )
                                        .background(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        .padding(
                                            8.dp
                                        ),
                                ) {
                                    Text(
                                        text = stringResource(R.string.primary_address),
                                        color = colorWhite
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = item.recipientName,
                            )
                            Text(
                                text = item.recipientAddress,
                            )
                            Text(
                                text = item.recipientPhone,
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            CustomOutlinedButton(
                                onClick = {
                                    val jsonString = Json.encodeToString(item)
                                    val encodedJson = URLEncoder.encode(jsonString, "UTF-8")

                                    navController.navigate(AppScreen.ShippingAddressFormScreen.route + "?isEdit=true&item=$encodedJson")

                                },
                                buttonText = stringResource(R.string.edit_shipping_address),
                                buttonBorderColor = MaterialTheme.colorScheme.primary
                            )
                        }


                    }
                }

            }
        }
    }
}

@Serializable
data class ShippingItem(
    val recipientName: String,
    val recipientPhone: String,
    val recipientAddress: String,
    val isPrimary: Boolean,
)
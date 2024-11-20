package com.luckyfrog.quickmart.features.cart.presentation.my_cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.cart.presentation.my_cart.component.QuantitySelector

@Composable
fun MyCartScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.menu_my_cart),
                navController = navController,
                centeredTitle = true,
                actions = {
                    TextButton(
                        onClick = { },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.voucher_code),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            )
        },

        ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(24.dp),
            state = rememberLazyListState()
        ) {
            items(3) { index ->
                val checked = remember {
                    mutableStateOf(false)
                }
                var quantity by remember { mutableIntStateOf(1) }

                Row(
                    modifier = Modifier,
                    content = {
                        AsyncImage(
                            model = "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                            contentDescription = "image",
                            modifier = Modifier
                                .size(120.dp, 120.dp)
                                .clip(
                                    RoundedCornerShape(12.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            horizontalAlignment = Alignment.Start,
                            content = {
                                Row(modifier = Modifier) {
                                    Text(
                                        text = "Loop Silicone Strong Magnetic Watch",
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Checkbox(checked = checked.value, onCheckedChange = { value ->
                                        checked.value = value
                                    })

                                }
                                Text(
                                    text = "\$15.25",
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text = "\$20.00",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 10.sp,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    QuantitySelector(
                                        quantity = quantity,
                                        onQuantityChanged = { newQuantity ->
                                            quantity = newQuantity
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    )
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Image(
                                            painter = painterResource(id = Images.icDeleteCart),
                                            contentDescription = "delete cart"
                                        )
                                    }
                                }
                            },
                        )
                    }
                )

            }

        }

    }

}
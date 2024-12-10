package com.luckyfrog.quickmart.features.product.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomFilterBottomSheet
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    navController: NavController,
) {
    val searchController =
        remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var selectedFilter by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }
    val recentSearchItems = listOf(
        "Laptop",
        "iPhone",
        "Tablet",
        "Television",
        "Airpods",
    )
    Scaffold(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .background(
                MaterialTheme.colorScheme.background
            ),

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(
                        when (mainViewModel.stateApp.theme) {
                            AppTheme.Light -> Images.icLogoDark
                            AppTheme.Dark -> Images.icLogoLight
                            AppTheme.Default -> if (isSystemInDarkTheme()) Images.icLogoLight else Images.icLogoDark
                        }
                    ),
                    modifier = Modifier.height(32.dp),
                    contentDescription = "Logo"
                )
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                    )
                }

            }
        },

        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
        ) {
            CustomTextField(

                value = searchController.value,
                onValueChange = { newText ->
                    searchController.value = newText
                },
                withTitleLabel = false,
                titleLabel = "",
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.search),
                leadingIcon = {
                    Image(
                        painter = painterResource(Images.icSearch),
                        modifier = Modifier.height(24.dp),
                        contentDescription = "Search",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)

                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        showBottomSheet = true

                    }) {
                        Image(
                            painter = painterResource(Images.icFilter),
                            modifier = Modifier.height(24.dp),
                            contentDescription = "Filter",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.recent_search).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn {
                items(recentSearchItems.size) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    4.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Text(
                                text = recentSearchItems[item],
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            IconButton(
                                onClick = {
                                    searchController.value = recentSearchItems[item]
                                }
                            ) {
                                Image(
                                    painter = painterResource(Images.icRecentSearchSubmit),
                                    contentDescription = "Recent Search",
                                    modifier = Modifier.height(24.dp)
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }

            // Screen content
            if (showBottomSheet) {

                ModalBottomSheet(
                    containerColor = MaterialTheme.colorScheme.background,
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    CustomFilterBottomSheet(
                        selectedFilter = selectedFilter,
                        onFilterSelected = { value ->
                            selectedFilter = value
                        },
                        onApply = {
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }

}
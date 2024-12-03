package com.luckyfrog.quickmart.core.app

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.luckyfrog.quickmart.core.di.apiModule
import com.luckyfrog.quickmart.core.di.appModule
import com.luckyfrog.quickmart.core.di.dataSourceModule
import com.luckyfrog.quickmart.core.di.dispatcherModule
import com.luckyfrog.quickmart.core.di.ktorModule
import com.luckyfrog.quickmart.core.di.repositoryModule
import com.luckyfrog.quickmart.core.di.useCaseModule
import com.luckyfrog.quickmart.core.di.viewModelModule
import com.luckyfrog.quickmart.utils.resource.route.NavGraph
import com.luckyfrog.quickmart.utils.resource.theme.MyAppTheme
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val preferences = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
            MyAppTheme(appTheme = viewModel.stateApp.theme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KoinApplication(
                        moduleList = {  listOf(
                            ktorModule,
                            apiModule,
                            dataSourceModule,
                            repositoryModule,
//            sqlDelightModule,
//            mapperModule,
                            dispatcherModule,
                            useCaseModule,
                            viewModelModule,
                            appModule(preferences),
                        )
                    }) {
                        NavGraph(viewModel)

                    }
                }
            }
        }
    }
}


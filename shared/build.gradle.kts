plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "2.0.21"

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.runtime)
            implementation(libs.ktor.client.core.v202)
            implementation(libs.ktor.client.content.negotiation.v202)
            implementation(libs.ktor.serialization.kotlinx.json.v202)

            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)
            //implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            implementation(libs.ktor.client.cio)

            //implementation("io.ktor:ktor-client-logging-native:$ktorVersion")

            implementation(libs.jetbrains.kotlinx.datetime.v040)

            implementation( libs.koin.core)

            //implementation("io.insert-koin:koin-test:${koin_core_version}")
            implementation(libs.kotlinx.coroutines.core.v162)
            implementation(libs.jetbrains.kotlinx.datetime.v040)

            // Datetime
            implementation(libs.kotlinx.datetime)

            // Koin
            implementation(libs.koin.core)

            // Kotlin Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // SQLDelight
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)

            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)

            // Android-specific SQLDelight driver
            implementation(libs.sqldelight.android.driver)

            // Android-specific SQLDelight driver
            implementation(libs.sqldelight.android.driver)

            // Ktor
            implementation(libs.ktor.client.android)
            implementation(libs.android.driver)

            // AndroidX Compose
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.androidx.espresso.core)

            // Compose Ui
            implementation(libs.androidx.ui)
            implementation(libs.androidx.ui.graphics)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            // Material3
            implementation(libs.androidx.material3)

            // Navigation
            implementation(libs.accompanist.navigation.animation)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.navigation.runtime.ktx)

            // Couroutine
            implementation(libs.kotlinx.coroutines.android)

            // Lottie
            implementation(libs.lottie.compose)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.accompanist.coil)


            // Toast
            implementation(libs.composablesweettoast)
            implementation(libs.paperdb)
            implementation(libs.multiplatform.settings.android.debug)

            api(libs.koin.core)
            api(libs.koin.compose)

        }

        iosMain.dependencies {
            // iOS-specific SQLDelight driver
            implementation(libs.sqldelight.native.driver)

            // Ktor
            implementation(libs.ktor.client.darwin)
            implementation(libs.native.driver)

        }
    }
}

android {
    namespace = "com.luckyfrog.quickmart"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(project(":androidApp"))
}

sqldelight {
    databases {
        create("CartDatabase") {
            packageName.set("com.luckyfrog.quickmart.features.cart.data.datasources.local")
        }
        create("WishlistDatabase") {
            packageName.set("com.luckyfrog.quickmart.features.wishlist.data.datasources.local")
        }
    }
}

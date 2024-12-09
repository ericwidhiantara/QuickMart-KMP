import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "2.0.21"
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
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
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.auth)

            implementation(libs.runtime)
            implementation(libs.json)

            // Datetime
            implementation(libs.kotlinx.datetime)

            // Koin
            implementation(libs.koin.core)
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


            implementation(libs.androidx.annotation.jvm)
            implementation(libs.androidx.runtime.android)
            implementation(libs.androidx.ui.android)
            implementation(libs.androidx.activity.ktx)
            implementation(libs.androidx.foundation.layout.android)
            implementation(libs.androidx.material3.android)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose.v343)
            implementation(libs.androidx.navigation.runtime.ktx)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.material.icons.extended)
            implementation(libs.composablesweettoast)
            implementation(libs.multiplatform.settings.android.debug)
            implementation(libs.androidx.preference.ktx)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

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
    buildFeatures {
        compose = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

sqldelight {
    databases {
        create("CartDatabase") {
            packageName.set("com.luckyfrog.quickmart.carts")
            srcDirs.setFrom("src/commonMain/sqldelight-carts")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight-carts"))
            generateAsync = false
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }

        create("WishlistDatabase") {
            packageName.set("com.luckyfrog.quickmart.wishlist")
            srcDirs.setFrom("src/commonMain/sqldelight-wishlist")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight-wishlist"))
            generateAsync = false
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }
    }
}


plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false

    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

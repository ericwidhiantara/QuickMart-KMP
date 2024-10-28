// Define versions as properties
val kotlinCompilerExtensionVersion by extra("1.4.6")

// Sdk and tools
val minSdkVersion = 21
val targetSdkVersion = 33
val compileSdkVersion = 33

// Gradle and Kotlin
val gradleVersion by extra("8.0.1")
val kotlinVersion by extra("1.8.20")

// App dependencies
val ktxVersion by extra("1.11.0-beta01")
val lifecycleVersion by extra("2.6.1")
val composeActivityVersion by extra("1.7.2")
val androidXComposeVersion by extra("2023.05.01")
val junitVersion by extra("4.13.2")
val testExtJunitVersion by extra("1.1.5")
val espressoVersion by extra("3.5.1")
val constraintVersion by extra("1.0.1")
val hiltVersion by extra("2.46.1")
val hiltAndroidXVersion by extra("1.1.0-alpha01")
val coroutinesVersion by extra("1.7.1")
val retrofitVersion by extra("2.9.0")
val okhttpVersion by extra("5.0.0-alpha.2")
val moshiVersion by extra("1.15.0")
val paperVersion by extra("2.7.2")
val lottieVersion by extra("6.0.0")
val systemUIControllerVersion by extra("0.28.0")
val navigationVersion by extra("2.7.0-alpha01")
val navAnimationVersion by extra("0.31.3-beta")
val coilVersion by extra("2.2.2")
val pagingVersion by extra("3.2.0-alpha06")


plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false

    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

package com.luckyfrog.quickmart.utils.resource.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class AppTheme {
    Light, Dark, Default
}

private val DarkColorScheme = darkColorScheme(
    primary = colorCyan, // Darker cyan variant
    onPrimary = Color.White, // Contrasting white on dark cyan
    primaryContainer = colorCyan50Dark,
    onPrimaryContainer = colorCyan50Dark,

    secondary = colorGrey150Dark,
    onSecondary = Color.White, // White on grey
    secondaryContainer = colorGrey50Dark,
    onSecondaryContainer = colorGrey150Dark,

    tertiary = colorPurple,
    onTertiary = Color.White, // White on purple
    tertiaryContainer = colorGrey50Dark,
    onTertiaryContainer = colorPurple,

    background = colorGrey50Dark,
    onBackground = Color.White, // White for readable text on dark background

    surface = colorGrey50Dark,
    onSurface = Color.White,
    surfaceVariant = colorGrey150Dark,
    onSurfaceVariant = colorGrey150,

    error = colorRed,
    onError = Color.White,
    errorContainer = colorGrey50Dark,
    onErrorContainer = colorRed,

    inversePrimary = colorCyan50,
    inverseSurface = colorGrey50,
    inverseOnSurface = colorGrey150,

    outline = colorGrey150,
    outlineVariant = colorGrey150Dark,
    scrim = Color.White.copy(alpha = 0.3f), // Semi-transparent white for overlays

    // Optional surface tints
    surfaceTint = colorCyan50Dark,
    surfaceContainer = colorGrey150Dark,
    surfaceContainerHigh = colorGrey100,
    surfaceContainerLowest = colorGrey50Dark,
    surfaceBright = colorGrey50,
    surfaceDim = colorGrey150Dark
)


private val LightColorScheme = lightColorScheme(
    primary = colorCyan,
    onPrimary = Color.White, // Color on top of cyan, should contrast well
    primaryContainer = colorCyan50,
    onPrimaryContainer = colorCyan50Dark, // Contrasts well with light cyan

    secondary = colorGrey100,
    onSecondary = Color.Black, // Dark color for text/icons
    secondaryContainer = colorGrey50,
    onSecondaryContainer = colorGrey150,

    tertiary = colorPurple,
    onTertiary = Color.White, // White text/icons for purple background
    tertiaryContainer = colorGrey50,
    onTertiaryContainer = colorPurple,

    background = colorGrey50,
    onBackground = Color.Black, // Black for readable text on light background

    surface = Color.White, // Default light surface
    onSurface = Color.Black,
    surfaceVariant = colorGrey50,
    onSurfaceVariant = colorGrey150,

    error = colorRed,
    onError = Color.White, // White text/icons on red
    errorContainer = colorGrey50,
    onErrorContainer = colorRed,

    inversePrimary = colorCyan, // Same as primary for light theme
    inverseSurface = colorGrey50Dark, // Dark version of background for inversion
    inverseOnSurface = colorGrey150Dark,

    outline = colorGrey150,
    outlineVariant = colorGrey100,
    scrim = Color.Black.copy(alpha = 0.3f), // Semi-transparent black for overlays

    // Optional surface tints
    surfaceTint = colorCyan50,
    surfaceContainer = colorGrey50,
    surfaceContainerHigh = colorGrey100,
    surfaceContainerLowest = colorGrey50,
    surfaceBright = Color.White,
    surfaceDim = colorGrey150
)


@Composable
fun MyAppTheme(
    appTheme: AppTheme,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appTheme) {
        AppTheme.Default -> {
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (isDarkMode) DarkColorScheme else LightColorScheme
                }

                isDarkMode -> DarkColorScheme
                else -> LightColorScheme
            }
        }

        AppTheme.Light -> {
            LightColorScheme
        }

        AppTheme.Dark -> {
            DarkColorScheme
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkMode
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.luckyfrog.quickmart.utils.resource.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val colorSecondary = Color(0xFF2567E8)

val colorBlack = Color(0xFF1C1B1B)
val colorBlack100 = Color(0xFF17202A)

val colorWhite = Color(0xFFFFFFFF)
val colorWhite100 = Color(0xFFE7E7E7)

val colorGrayDark = Color(0xFF0E141C)

val colorCyan = Color(0xff21D4B4)
val colorCyan50 = Color(0xFFF4FDFA)
val colorCyan50Dark = Color(0xFF212322)

val colorBackground = colorWhite
val colorBackgroundDark = Color(0xff1C1B1B)

val colorGrey50 = Color(0xFFF4F5FD)
val colorGrey50Dark = Color(0xFF1C1B1B)
val colorGrey100 = Color(0xFFC0C0C0)
val colorGrey150 = Color(0xFF6F7384)
val colorGrey150Dark = Color(0xFFA2A2A6)

val borderColorDark = Color(0xff282828)
val borderColorLight = Color(0xffF4F5FD)

val colorRed = Color(0xFFEE4D4D)
val colorGreen = Color(0xFF08E488)
val colorBlue = Color(0xFF1F88DA)
val colorPurple = Color(0xFF4F1FDA)
val colorYellow = Color(0xFFEBEF14)
val colorOrange = Color(0xFFF0821D)
val colorMerigold = Color(0xFFFFCB45)
val colorBrown = Color(0xFF5A1A05)
val colorPink = Color(0xFFCE1DEB)

val ColorScheme.borderColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) borderColorDark else borderColorLight
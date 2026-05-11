package com.ivanmadrid.vehiclecontrolapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AppBlueDark,
    onPrimary = Color(0xFF081A4A),
    primaryContainer = Color(0xFF1E2440),
    onPrimaryContainer = AppBlueDark,
    secondary = AppGreenDark,
    tertiary = AppOrangeDark,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    error = AppRedDark,
    errorContainer = DarkSoftRed,
    onErrorContainer = DarkText
)

private val LightColorScheme = lightColorScheme(
    primary = AppBlue,
    onPrimary = Color.White,
    primaryContainer = LightSoftBlue,
    onPrimaryContainer = AppBlue,
    secondary = AppGreen,
    tertiary = AppOrange,
    background = LightBackground,
    onBackground = LightText,
    surface = LightSurface,
    onSurface = LightText,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
    error = AppRed,
    errorContainer = LightSoftRed,
    onErrorContainer = AppRed
)

@Immutable
data class VehicleColors(
    val blue: Color,
    val green: Color,
    val orange: Color,
    val red: Color,
    val purple: Color,
    val softBlue: Color,
    val softGreen: Color,
    val softYellow: Color,
    val softRed: Color,
    val softPurple: Color,
    val divider: Color,
    val avatarSurface: Color
)

val LightVehicleColors = VehicleColors(
    blue = AppBlue,
    green = AppGreen,
    orange = AppOrange,
    red = AppRed,
    purple = Color(0xFF6F35D4),
    softBlue = LightSoftBlue,
    softGreen = LightSoftGreen,
    softYellow = LightSoftYellow,
    softRed = LightSoftRed,
    softPurple = LightSoftPurple,
    divider = LightBorder,
    avatarSurface = Color.White
)

val DarkVehicleColors = VehicleColors(
    blue = AppBlueDark,
    green = AppGreenDark,
    orange = AppOrangeDark,
    red = AppRedDark,
    purple = Color(0xFFC4B5FD),
    softBlue = DarkSoftBlue,
    softGreen = DarkSoftGreen,
    softYellow = DarkSoftYellow,
    softRed = DarkSoftRed,
    softPurple = DarkSoftPurple,
    divider = DarkBorder,
    avatarSurface = DarkSurfaceVariant
)

val LocalVehicleColors = staticCompositionLocalOf {
    LightVehicleColors
}

val MaterialTheme.vehicleColors: VehicleColors
    @Composable
    get() = LocalVehicleColors.current

@Composable
fun VehicleControlAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val vehicleColors = if (darkTheme) DarkVehicleColors else LightVehicleColors

    CompositionLocalProvider(LocalVehicleColors provides vehicleColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

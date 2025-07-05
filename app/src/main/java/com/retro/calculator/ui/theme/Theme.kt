package com.retro.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = RetroColors.Primary,
    secondary = RetroColors.Accent,
    tertiary = RetroColors.DarkBlue,
    background = RetroColors.BgDark,
    surface = RetroColors.BgCard,
    onPrimary = Color.White,
    onSecondary = RetroColors.DarkBlue,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun RetroCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = RetroTypography,
        content = content
    )
}
package com.soechka1.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PomodoroColorPalette(
    val background: Color,
    val surface: Color,
    val border: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnAccent: Color,
    val accentGreen: Color,
    val accentYellow: Color,
    val accentBlue: Color,
    val accentPink: Color,
    val accentOrange: Color,
    val success: Color,
    val error: Color,
)

internal val LightPomodoroColorPalette = PomodoroColorPalette(
    background = Color(0xFFFFF9F0),
    surface = Color(0xFFFFFFFF),
    border = Color(0xFF171717),
    textPrimary = Color(0xFF171717),
    textSecondary = Color(0xFF5F5A67),
    textOnAccent = Color(0xFF171717),
    accentGreen = Color(0xFFC8F169),
    accentYellow = Color(0xFFFFD86E),
    accentBlue = Color(0xFF8EDBFF),
    accentPink = Color(0xFFFFB5DF),
    accentOrange = Color(0xFFFFA37A),
    success = Color(0xFF41B66E),
    error = Color(0xFFFF6B57),
)

internal val DarkPomodoroColorPalette = PomodoroColorPalette(
    background = Color(0xFF121114),
    surface = Color(0xFF1D1B20),
    border = Color(0xFFF7EBDD),
    textPrimary = Color(0xFFF7EBDD),
    textSecondary = Color(0xFFD1C4D7),
    textOnAccent = Color(0xFF171717),
    accentGreen = Color(0xFF9FD647),
    accentYellow = Color(0xFFE2B943),
    accentBlue = Color(0xFF55B7E8),
    accentPink = Color(0xFFE58BC2),
    accentOrange = Color(0xFFE28157),
    success = Color(0xFF61D18D),
    error = Color(0xFFFF8B78),
)

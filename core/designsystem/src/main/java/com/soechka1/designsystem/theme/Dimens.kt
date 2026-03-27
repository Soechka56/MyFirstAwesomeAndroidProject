package com.soechka1.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PomodoroSpacingTokens(
    val xSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val xLarge: Dp,
    val xxLarge: Dp,
)

internal val DefaultPomodoroSpacing = PomodoroSpacingTokens(
    xSmall = 4.dp,
    small = 8.dp,
    medium = 12.dp,
    large = 16.dp,
    xLarge = 24.dp,
    xxLarge = 32.dp,
)

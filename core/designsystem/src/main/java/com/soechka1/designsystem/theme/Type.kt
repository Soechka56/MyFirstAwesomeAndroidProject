package com.soechka1.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.designsystem.R


private val BagelFontFamily = FontFamily(
    Font(
        resId = R.font.bagelfont,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    )
)

private val YrsaFontFamily = FontFamily(
    Font(
        resId = R.font.yrsafont,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    )
)

private val AksharFontFamily = FontFamily(
    Font(
        resId = R.font.aksharfont,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    )
)

@Immutable
data class PomodoroTypographyTokens(
    val display: TextStyle,
    val title: TextStyle,
    val titleSmall: TextStyle,
    val body: TextStyle,
    val bodyLarge: TextStyle,
    val label: TextStyle,
    val timerValue: TextStyle,
)

internal fun pomodoroTypography(colors: PomodoroColorPalette) = PomodoroTypographyTokens(
    display = TextStyle(
        fontFamily = BagelFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        color = colors.textPrimary,
    ),
    title = TextStyle(
        fontFamily = BagelFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        color = colors.textPrimary,
    ),
    titleSmall = TextStyle(
        fontFamily = BagelFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        color = colors.textPrimary,
    ),
    body = TextStyle(
        fontFamily = YrsaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = colors.textSecondary,
    ),
    bodyLarge = TextStyle(
        fontFamily = YrsaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = colors.textSecondary,
    ),
    label = TextStyle(
        fontFamily = AksharFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        color = colors.textPrimary,
    ),
    timerValue = TextStyle(
        fontFamily = YrsaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        color = colors.textSecondary,
    ),
)

internal fun materialTypography(colors: PomodoroColorPalette) = Typography(
    displayLarge = pomodoroTypography(colors).display,
    titleLarge = pomodoroTypography(colors).title,
    bodyLarge = pomodoroTypography(colors).bodyLarge,
    bodyMedium = pomodoroTypography(colors).body,
    labelLarge = pomodoroTypography(colors).label,
)

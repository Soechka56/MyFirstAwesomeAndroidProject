package com.soechka1.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val LocalPomodoroColors = staticCompositionLocalOf { LightPomodoroColorPalette }
private val LocalPomodoroTypography = staticCompositionLocalOf {
    pomodoroTypography(LightPomodoroColorPalette)
}
private val LocalPomodoroShapes = staticCompositionLocalOf { pomodoroShapes() }
private val LocalPomodoroSpacing = staticCompositionLocalOf { DefaultPomodoroSpacing }

private fun lightPomodoroMaterialColors(colors: PomodoroColorPalette): ColorScheme = lightColorScheme(
    primary = colors.accentGreen,
    secondary = colors.accentBlue,
    tertiary = colors.accentPink,
    background = colors.background,
    surface = colors.surface,
    onPrimary = colors.textOnAccent,
    onSecondary = colors.textOnAccent,
    onTertiary = colors.textOnAccent,
    onBackground = colors.textPrimary,
    onSurface = colors.textPrimary,
    outline = colors.border,
    error = colors.error,
)

private fun darkPomodoroMaterialColors(colors: PomodoroColorPalette): ColorScheme = darkColorScheme(
    primary = colors.accentGreen,
    secondary = colors.accentBlue,
    tertiary = colors.accentPink,
    background = colors.background,
    surface = colors.surface,
    onPrimary = colors.textOnAccent,
    onSecondary = colors.textOnAccent,
    onTertiary = colors.textOnAccent,
    onBackground = colors.textPrimary,
    onSurface = colors.textPrimary,
    outline = colors.border,
    error = colors.error,
)

object MyFirstAwesomeAndroidProjectThemeTokens {
    val colors: PomodoroColorPalette
        @Composable
        @ReadOnlyComposable
        get() = LocalPomodoroColors.current

    val typography: PomodoroTypographyTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalPomodoroTypography.current

    val shapes: PomodoroShapeTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalPomodoroShapes.current

    val spacing: PomodoroSpacingTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalPomodoroSpacing.current
}

@Composable
fun MyFirstAwesomeAndroidProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val pomodoroColors = if (darkTheme) DarkPomodoroColorPalette else LightPomodoroColorPalette
    val typography = pomodoroTypography(pomodoroColors)
    val shapes = pomodoroShapes()

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkPomodoroMaterialColors(pomodoroColors)
        else -> lightPomodoroMaterialColors(pomodoroColors)
    }

    CompositionLocalProvider(
        LocalPomodoroColors provides pomodoroColors,
        LocalPomodoroTypography provides typography,
        LocalPomodoroShapes provides shapes,
        LocalPomodoroSpacing provides DefaultPomodoroSpacing,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = materialTypography(pomodoroColors),
            shapes = materialShapes(),
            content = content,
        )
    }
}

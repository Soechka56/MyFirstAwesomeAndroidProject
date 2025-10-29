package com.soechka1.myfirstawesomeandroidproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.soechka1.myfirstawesomeandroidproject.ui.AppTheme

private val PurpleLightScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = White,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = LightGray,
    surface = White,
)

private val PurpleDarkScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Black,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = DarkBackground,
    surface = DarkSurface,
)

private val RedLightScheme = lightColorScheme(
    primary = RedDark,
    onPrimary = White,
    secondary = RedMain,
    tertiary = RedMain,
    background = RedLight,
    surface = White,
)

private val RedDarkScheme = darkColorScheme(
    primary = RedMain,
    onPrimary = Black,
    secondary = RedDark,
    tertiary = RedMain,
    background = RedVeryDark,
    surface = RedDarkSurface,
)

private val GreenLightScheme = lightColorScheme(
    primary = GreenDark,
    onPrimary = White,
    secondary = GreenMain,
    tertiary = GreenMain,
    background = GreenLight,
    surface = White,
)

private val GreenDarkScheme = darkColorScheme(
    primary = GreenMain,
    onPrimary = Black,
    secondary = GreenDark,
    tertiary = GreenMain,
    background = GreenVeryDark,
    surface = GreenDarkSurface,
)

@Composable
fun MyFirstAwesomeAndroidProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    theme: AppTheme = AppTheme.PURPLE,
    content: @Composable () -> Unit
) {
    // where theme auto apply
    val colorScheme = when (theme) {
        AppTheme.PURPLE -> if (darkTheme) PurpleDarkScheme else PurpleLightScheme
        AppTheme.RED -> if (darkTheme) RedDarkScheme else RedLightScheme
        AppTheme.GREEN -> if (darkTheme) GreenDarkScheme else GreenLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

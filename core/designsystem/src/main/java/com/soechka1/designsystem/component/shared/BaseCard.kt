package com.soechka1.designsystem.component.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens


// shared card container, clip + background + border.
@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    shape: Shape? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),

    content: @Composable BoxScope.() -> Unit,
) {
    val colors = MyFirstAwesomeAndroidProjectThemeTokens.colors
    val shapes = MyFirstAwesomeAndroidProjectThemeTokens.shapes
    val resolvedShape = shape ?: shapes.card

    Box(
        modifier = modifier
            .clip(resolvedShape)
            .background(backgroundColor ?: colors.surface, resolvedShape)
            .border(2.dp, colors.border, resolvedShape)
            .padding(contentPadding),
        content = content,
    )
}

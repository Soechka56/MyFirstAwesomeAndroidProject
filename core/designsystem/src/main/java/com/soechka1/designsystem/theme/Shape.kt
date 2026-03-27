package com.soechka1.designsystem.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class PomodoroShapeTokens(
    val card: Shape,
    val control: Shape,
    val circle: Shape,
)

internal fun pomodoroShapes() = PomodoroShapeTokens(
    card = RoundedCornerShape(24.dp),
    control = RoundedCornerShape(18.dp),
    circle = CircleShape,
)

internal fun materialShapes() = Shapes(
    small = RoundedCornerShape(18.dp),
    medium = RoundedCornerShape(24.dp),
    large = RoundedCornerShape(24.dp),
)

package com.soechka1.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Immutable
data class PomodoroSizes(
    val cardS: DpSize,
    val cardM: DpSize,
    val cardXL: DpSize,

    val avatar: Dp,
    val iconS: Dp,
    val iconM: Dp,
    val iconL: Dp,

    val minTouchTarget: Dp,

    )

val SizesCompact = PomodoroSizes(
    cardS = DpSize(156.dp, 132.dp),
    cardM = DpSize(220.dp, 168.dp),
    cardXL = DpSize(320.dp, 120.dp),

    avatar = 56.dp,
    iconS = 18.dp,
    iconM = 22.dp,
    iconL = 28.dp,

    minTouchTarget = 48.dp
)

val SizesMedium = SizesCompact.copy(
    cardM = DpSize(240.dp, 176.dp),
    cardXL = DpSize(360.dp, 132.dp),
)

val SizesExpanded = SizesCompact.copy(
    cardM = DpSize(260.dp, 184.dp),
    cardXL = DpSize(420.dp, 140.dp),
)

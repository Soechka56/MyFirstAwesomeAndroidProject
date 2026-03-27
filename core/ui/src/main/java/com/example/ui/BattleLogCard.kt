package com.example.ui

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.BattleLogItemModel
import com.soechka1.designsystem.component.shared.BaseCard
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens

@Composable
fun BattleLogCard(
    battle: BattleLogItemModel,
    modifier: Modifier = Modifier,
) {
    val colors = MyFirstAwesomeAndroidProjectThemeTokens.colors
    val typography = MyFirstAwesomeAndroidProjectThemeTokens.typography
    val spacing = MyFirstAwesomeAndroidProjectThemeTokens.spacing
    val battleMode = battle.mode ?: stringResource(id = R.string.battle_log_unknown_mode)
    val battleResult = battle.result ?: stringResource(id = R.string.battle_log_finished)
    val backgroundColor = battle.result.toBattleBackgroundColor(
        victoryColor = colors.accentGreen,
        defeatColor = colors.error,
        defaultColor = colors.surface,
    )
    val contentColor = if (backgroundColor == colors.surface) {
        colors.textPrimary
    } else {
        colors.textOnAccent
    }

    BaseCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(spacing.large),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                BattleBadge(text = battleMode)
                Text(
                    text = formatBattleTime(battle.battleTime),
                    style = typography.body,
                    color = contentColor,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.xSmall),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = battleResult,
                    style = typography.title,
                    color = contentColor,
                )
                Text(
                    text = stringResource(id = R.string.battle_log_id, battle.id ?: "-"),
                    style = typography.body,
                    color = contentColor,
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.battle_log_trophies),
                    style = typography.label,
                    color = contentColor,
                )
                Text(
                    text = formatTrophyChange(
                        trophyChange = battle.trophyChange,
                        notAvailable = stringResource(id = R.string.battle_log_not_available),
                    ),
                    style = typography.display,
                    color = contentColor,
                )
            }
        }
    }
}

@Composable
private fun BattleBadge(text: String) {
    val colors = MyFirstAwesomeAndroidProjectThemeTokens.colors
    val typography = MyFirstAwesomeAndroidProjectThemeTokens.typography
    val shapes = MyFirstAwesomeAndroidProjectThemeTokens.shapes

    Box(
        modifier = Modifier
            .background(colors.accentYellow, shapes.control)
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            style = typography.label,
        )
    }
}

private fun formatTrophyChange(trophyChange: Int?, notAvailable: String): String {
    if (trophyChange == null) return notAvailable
    return if (trophyChange > 0) "+$trophyChange" else trophyChange.toString()
}

private fun formatBattleTime(battleTime: String): String {
    return runCatching {
        LocalDateTime.parse(
            battleTime,
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'")
        ).format(DateTimeFormatter.ofPattern("dd MMM, HH:mm"))
    }.getOrDefault(battleTime)
}

private fun String?.toBattleBackgroundColor(
    victoryColor: Color,
    defeatColor: Color,
    defaultColor: Color,
): Color {
    return when (this?.lowercase()) {
        "victory" -> victoryColor
        "defeat" -> defeatColor
        else -> defaultColor
    }
}

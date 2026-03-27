package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.domain.model.BattleLogDetailsModel
import com.soechka1.designsystem.component.shared.BaseCard
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens

@Composable
fun PlayerCard(
    player: BattleLogDetailsModel.Player,
    backgroundColor: Color,
    cdnBaseUrl: String,
    modifier: Modifier = Modifier,
) {
    val typography = MyFirstAwesomeAndroidProjectThemeTokens.typography
    val spacing = MyFirstAwesomeAndroidProjectThemeTokens.spacing

    val playerName = player.name ?: stringResource(R.string.player_unknown_name)
    val playerTag = player.tag ?: stringResource(R.string.player_no_tag)
    val powerText = player.power?.let { stringResource(R.string.player_power_value, it) }
        ?: stringResource(R.string.player_unknown_power)
    val trophiesText = player.trophies?.let { stringResource(R.string.player_trophies_value, it) }
        ?: stringResource(R.string.player_no_trophies)

    BaseCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(spacing.medium),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PlayerAvatar(
                brawlerId = player.brawlerId,
                brawlerName = player.brawlerName,
                cdnBaseUrl = cdnBaseUrl
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.xSmall),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = playerName,
                    style = typography.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = playerTag,
                    style = typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "$powerText • $trophiesText",
                    style = typography.body,
                )
            }
        }
    }
}

@Composable
private fun PlayerAvatar(
    brawlerId: Int?,
    brawlerName: String?,
    cdnBaseUrl: String
) {
    val colors = MyFirstAwesomeAndroidProjectThemeTokens.colors
    val typography = MyFirstAwesomeAndroidProjectThemeTokens.typography
    val shapes = MyFirstAwesomeAndroidProjectThemeTokens.shapes

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(72.dp)
            .background(colors.surface, shapes.circle)
            .border(2.dp, colors.border, shapes.circle)
            .clip(shapes.circle),
    ) {
        if (brawlerId != null) {
            AsyncImage(
                model = "$cdnBaseUrl/brawlers/borders/$brawlerId.png",
                contentDescription = brawlerName,
                contentScale = ContentScale.Fit,
                modifier = Modifier.scale(1.2f)
            )
        } else {
            Text(
                text = if (!brawlerName.isNullOrBlank()) {
                    brawlerName.take(2).uppercase()
                } else {
                    stringResource(R.string.player_unknown_avatar)
                },
                style = typography.label,
            )
        }
    }
}

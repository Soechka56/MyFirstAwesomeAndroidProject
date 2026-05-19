package com.example.impl

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens
import com.example.ui.PlayerCard
import com.soechka1.myfirstawesomeandroidproject.feature.getbattlelog.impl.R
import com.example.impl.model.GetBattleLogScreenEvent

@Composable
fun GetBattleLogScreen(
    battleId: Long,
    cdnBaseUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GetBattleLogViewModel = koinViewModel { parametersOf(battleId) },
) {
    val context = LocalContext.current
    val spacing = MyFirstAwesomeAndroidProjectThemeTokens.spacing
    val colors = MyFirstAwesomeAndroidProjectThemeTokens.colors
    
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is GetBattleLogScreenEvent.ShowSourceMessageRes -> {
                    Toast.makeText(context, context.getString(event.messageResId), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(modifier = modifier) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(spacing.large),
        ) {
            item {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = stringResource(id = R.string.get_battle_log_back))
                }
            }

            uiState.battleDetails?.let { details ->
                item {
                    Text(
                        text = stringResource(
                            id = R.string.battle_log_details_title,
                            details.mode ?: stringResource(id = R.string.battle_log_details_unknown),
                        ),
                    )
                }
                item {
                    Text(
                        text = stringResource(
                            id = R.string.battle_log_details_result,
                            details.result ?: stringResource(id = R.string.battle_log_details_unknown),
                        ),
                    )
                }
                item {
                    Text(
                        text = stringResource(
                            id = R.string.battle_log_details_duration,
                            details.duration ?: 0,
                        ),
                    )
                }

                itemsIndexed(details.teams) { index, players ->
                    Text(
                        text = if (index == 0) {
                            stringResource(id = R.string.battle_log_details_my_team)
                        } else {
                            stringResource(id = R.string.battle_log_details_enemy_team, index)
                        },
                    )
                    players.forEach { player ->
                        PlayerCard(
                            player = player.copy(),
                            backgroundColor = if (index == 0) colors.accentYellow else colors.accentBlue,
                            cdnBaseUrl = cdnBaseUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = spacing.small),
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                item {
                    Text(text = stringResource(id = R.string.get_battle_log_loading))
                }
            }

            uiState.errorMessage?.let {
                item {
                    Text(text = it)
                }
            }
        }
    }
}

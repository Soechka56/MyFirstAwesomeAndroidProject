package com.example.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.di.ServiceLocator
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens
import com.example.ui.PlayerCard
import android.widget.Toast
import com.soechka1.myfirstawesomeandroidproject.feature.getbattlelog.impl.R

class GetBattleLogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val battleId = intent.getLongExtra(EXTRA_BATTLE_ID, -1L)
        setContent {
            MyFirstAwesomeAndroidProjectTheme {
                val viewModel: GetBattleLogViewModel = viewModel(
                    factory = GetBattleLogViewModel.Factory,
                    extras = MutableCreationExtras(defaultViewModelCreationExtras).apply {
                        set(GetBattleLogViewModel.BattleIdKey, battleId)
                    },
                )
                val context = LocalContext.current
                val spacing = MyFirstAwesomeAndroidProjectThemeTokens.spacing
                val colors = MyFirstAwesomeAndroidProjectThemeTokens.colors
                val cdnBaseUrl = ServiceLocator.getBuildConfigProvider().getBrawlerCdnBaseUrl()

                viewModel.sourceEvent?.let { event ->
                    LaunchedEffect(event.first) {
                        Toast.makeText(context, event.second, Toast.LENGTH_SHORT).show()
                        viewModel.clearSourceEvent()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(spacing.large),
                    ) {
                        item {
                            Button(
                                onClick = ::finish,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(text = stringResource(id = R.string.get_battle_log_back))
                            }
                        }

                        viewModel.battleDetails?.let { details ->
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

                        if (viewModel.isLoading) {
                            item {
                                Text(text = stringResource(id = R.string.get_battle_log_loading))
                            }
                        }

                        viewModel.errorMessage?.let {
                            item {
                                Text(text = it)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val EXTRA_BATTLE_ID = "extra_battle_id"

        fun createIntent(context: Context, battleId: Long): Intent {
            return Intent(context, GetBattleLogActivity::class.java).apply {
                putExtra(EXTRA_BATTLE_ID, battleId)
            }
        }
    }
}

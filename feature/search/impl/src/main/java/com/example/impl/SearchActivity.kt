package com.example.impl

import android.widget.Toast
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soechka1.myfirstawesomeandroidproject.feature.search.impl.R
import com.soechka1.designsystem.component.shared.BaseCard
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens
import com.example.ui.BattleLogCard

class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAwesomeAndroidProjectTheme {
                val viewModel: SearchViewModel = viewModel(
                    factory = SearchViewModel.Factory,
                )
                SearchScreen(
                    viewModel = viewModel,
                    onBattleClick = { battle ->
                        battle.id?.let { battleId ->
                            startActivity(GetBattleLogActivity.createIntent(this, battleId))
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun SearchScreen(
    viewModel: SearchViewModel,
    onBattleClick: (com.example.domain.model.BattleLogItemModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val spacing = MyFirstAwesomeAndroidProjectThemeTokens.spacing

    viewModel.sourceEvent?.let { event ->
        LaunchedEffect(event.first) {
            Toast.makeText(context, event.second, Toast.LENGTH_SHORT).show()
            viewModel.clearSourceEvent()
        }
    }

    Scaffold(modifier = modifier) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(spacing.large),
        ) {
            item {
                Text(text = stringResource(id = R.string.search_title))
            }

            item {
                BaseCard(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(spacing.large),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(spacing.medium),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedTextField(
                            value = viewModel.hashtag,
                            onValueChange = viewModel::updateHashtag,
                            label = {
                                Text(text = stringResource(id = R.string.search_hashtag_label))
                            },
                            placeholder = {
                                Text(text = stringResource(id = R.string.search_hashtag_placeholder))
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Button(
                            onClick = viewModel::search,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = stringResource(id = R.string.search_button))
                        }
                    }
                }
            }

            if (viewModel.isLoading) {
                item {
                    Text(text = stringResource(id = R.string.search_loading))
                }
            }

            viewModel.errorMessage?.let {
                item {
                    Text(text = it)
                }
            }

            items(
                items = viewModel.battles,
                key = { it.id ?: it.battleTime },
            ) { battle ->
                BattleLogCard(
                    battle = battle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onBattleClick(battle) },
                )
            }
        }
    }
}

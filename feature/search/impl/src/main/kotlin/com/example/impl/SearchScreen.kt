package com.example.impl

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import com.soechka1.myfirstawesomeandroidproject.feature.search.impl.R
import com.soechka1.designsystem.component.shared.BaseCard
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens
import com.example.ui.BattleLogCard
import com.example.impl.model.SearchScreenEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.soechka1.designsystem.component.shared.MyFirstAwesomeCustomView
@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SearchScreen(
    onBattleClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val spacing = MyFirstAwesomeAndroidProjectThemeTokens.spacing
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SearchScreenEvent.ShowSourceMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(event.messageResId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        modifier = Modifier
            .fillMaxSize()
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
                        value = uiState.hashtag,
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

        if (uiState.isLoading) {
            item {
                Text(text = stringResource(id = R.string.search_loading))
            }
        }

        uiState.errorMessage?.let {
            item {
                Text(text = it)
            }
        }

        if (uiState.battlesCountByMode.isNotEmpty()) {
            item {
                BaseCard(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(spacing.large),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(spacing.medium),
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Modes played:",
                            style = MaterialTheme.typography.titleMedium
                        )

                        val modes = uiState.battlesCountByMode.keys.toList()

                        // only for test
                        val palette = listOf(
                            android.graphics.Color.RED,
                            android.graphics.Color.CYAN,
                            android.graphics.Color.GREEN,
                            android.graphics.Color.BLUE,
                            android.graphics.Color.YELLOW,
                            android.graphics.Color.MAGENTA
                        )

                        val colorsToUse = modes.mapIndexed { index, _ -> palette[index % palette.size] }

                        AndroidView(
                            factory = { ctx ->
                                MyFirstAwesomeCustomView(ctx).apply {
                                    sectorsCount = modes.size
                                    sectorColors = colorsToUse
                                }
                            },
                            update = { view ->
                                view.sectorsCount = modes.size
                                view.sectorColors = colorsToUse
                            },
                            modifier = Modifier
                                .padding(vertical = spacing.medium)
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        uiState.battlesCountByMode.entries.forEachIndexed { index, entry ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color(colorsToUse[index]))
                                )
                                Text(text = entry.key)
                            }
                        }
                    }
                }
            }
        }

        items(
            items = uiState.battles,
            key = { it.id ?: it.battleTime },
        ) { battle ->
            BattleLogCard(
                battle = battle,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        battle.id?.let { onBattleClick(it) }
                    },
            )
        }
    }

}

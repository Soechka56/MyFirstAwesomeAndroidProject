package com.soechka1.designsystem.autolayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectThemeTokens

@Composable
fun CustomStaggeredGrid(
    items: List<@Composable (() -> Unit)>,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    horizontalSpacing: Dp = MyFirstAwesomeAndroidProjectThemeTokens.spacing.large,
    verticalSpacing: Dp = MyFirstAwesomeAndroidProjectThemeTokens.spacing.large,
) {


    val distributedItems = List(columns) { columnIndex ->
        items.filterIndexed { index, _ -> (index % columns) == columnIndex }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
    ) {
        // pinterest like feed
        for (i in 0 until columns) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            ) {
                distributedItems[i].forEach { compFunc ->
                    compFunc()
                }
            }
        }
    }
}

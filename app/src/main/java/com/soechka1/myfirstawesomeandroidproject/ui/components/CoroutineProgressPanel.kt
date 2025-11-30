package com.soechka1.myfirstawesomeandroidproject.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CoroutineProgressPanel(
    modifier: Modifier = Modifier,
    currentProgress: Int, totalSteps: Int
){

    Column(modifier = modifier) {
        LinearProgressIndicator(
            progress = { currentProgress.toFloat() / totalSteps.toFloat() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
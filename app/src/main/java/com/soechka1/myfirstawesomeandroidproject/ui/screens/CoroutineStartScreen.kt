package com.soechka1.myfirstawesomeandroidproject.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.CoroutineSettings
import com.soechka1.myfirstawesomeandroidproject.ui.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.components.CoroutineControlButton
import com.soechka1.myfirstawesomeandroidproject.ui.components.CoroutineProgressPanel
import com.soechka1.myfirstawesomeandroidproject.ui.components.CoroutineSettingsPanel

@Composable
fun CoroutineStartScreen(
    modifier: Modifier,
    settings: CoroutineSettings,
    onSettingsChange: (CoroutineSettings) -> Unit,
    completedCount: Int,
    programStarted: Boolean,
    onExecutorStart: () -> Unit,
    onExecutorCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(Dimens.BASE_PADDING)
            .fillMaxSize()
    ) {
        CoroutineSettingsPanel(
            coroutineSettings = settings,
            onSettingsChange = onSettingsChange
        )

        Spacer(modifier = Modifier.height(Dimens.BASE_PADDING))

        Text(text = stringResource(R.string.total_completed_coroutines, completedCount))


        if (!programStarted) {
            CoroutineControlButton(
                buttonText = stringResource(R.string.button_execute),
                onButtonClick = onExecutorStart
            )
        } else {
            CoroutineProgressPanel(
                currentProgress = completedCount,
                totalSteps = settings.countOfCoroutine,
            )
            CoroutineControlButton(
                buttonText = stringResource(R.string.button_cancel),
                onButtonClick = onExecutorCancel
            )
        }
    }
}

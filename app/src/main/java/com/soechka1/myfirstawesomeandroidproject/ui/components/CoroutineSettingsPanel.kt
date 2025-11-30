package com.soechka1.myfirstawesomeandroidproject.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderPositions
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.model.CoroutineSettings
import com.soechka1.myfirstawesomeandroidproject.model.DispatcherType
import com.soechka1.myfirstawesomeandroidproject.model.SettingSwitch
import com.soechka1.myfirstawesomeandroidproject.ui.Dimens
import kotlin.math.ceil

// settings of slider
private const val MIN_COROUTINES = 10f
private const val MAX_COROUTINES = 100f
private const val COROUTINE_STEP = 5

@Composable
fun CoroutineSettingsPanel(
    modifier: Modifier = Modifier,
    coroutineSettings: CoroutineSettings,
    onSettingsChange: (CoroutineSettings) -> Unit,
) {

    Column(modifier = modifier) {
        var sliderPosition by remember(coroutineSettings.countOfCoroutine) {
            mutableFloatStateOf(coroutineSettings.countOfCoroutine.toFloat())
        }

        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = ceil(it / COROUTINE_STEP) * COROUTINE_STEP
                        onSettingsChange(coroutineSettings.copy(countOfCoroutine = sliderPosition.toInt())) },
                    steps = (MAX_COROUTINES - MIN_COROUTINES).toInt() / COROUTINE_STEP,
                    valueRange = MIN_COROUTINES..MAX_COROUTINES
                )
        Text(text = sliderPosition.toInt().toString())
    }


    DispatcherDropdown(
            selectedDispatcher = coroutineSettings.selectedDispatcher,
            onDispatcherSelected = { dispatcher ->
                onSettingsChange(coroutineSettings.copy(selectedDispatcher = dispatcher))
            },
            modifier = Modifier.fillMaxWidth()
        )


        SwitchRow(SettingSwitch.PARALLEL.name, coroutineSettings.isParallel,
            enabled = coroutineSettings.isParallel) {
            onSettingsChange(coroutineSettings.copy(isParallel = it))
        }

        SwitchRow(SettingSwitch.SEQUENTAL.name, !coroutineSettings.isParallel,
            enabled = !coroutineSettings.isParallel) {
            onSettingsChange(coroutineSettings.copy(isParallel = !it))
        }

        SwitchRow(SettingSwitch.LAZY.name, coroutineSettings.isLazyStart) {
            onSettingsChange(coroutineSettings.copy(isLazyStart = it))
        }

        SwitchRow(SettingSwitch.BACKGROUND.name, coroutineSettings.isBackground) {
            onSettingsChange(coroutineSettings.copy(isBackground = it))
        }
    }


@Composable
fun SwitchRow(
    label: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.BASE_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Spacer(Modifier.width(Dimens.BASE_PADDING))
        Switch(checked, onCheckedChange = onCheckedChange, enabled = enabled)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatcherDropdown(
    selectedDispatcher: DispatcherType,
    onDispatcherSelected: (DispatcherType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = stringResource(selectedDispatcher.labelRes),
            onValueChange = {},
            readOnly = true,
            label = { Text("Dispatcher") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DispatcherType.entries.forEach { dispatcher ->
                DropdownMenuItem(
                    text = { Text(stringResource(dispatcher.labelRes)) },
                    onClick = {
                        onDispatcherSelected(dispatcher)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

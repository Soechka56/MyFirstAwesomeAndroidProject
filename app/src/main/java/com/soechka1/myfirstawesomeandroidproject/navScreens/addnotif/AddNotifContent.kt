package com.soechka1.myfirstawesomeandroidproject.navScreens.addnotif

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.NotificationSettings
import com.soechka1.myfirstawesomeandroidproject.model.NotificationType
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.FontSizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotifContent(
    modifier: Modifier,
    stateHolder: AddNotifStateHolder,
    onShowButtonClicked: (Pair<String, String>, NotificationSettings) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.small)
    ) {
        TextField(
            value = stateHolder.label,
            onValueChange = stateHolder::onLabelChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.add_notif_title_label)) },
            placeholder = { Text(text = stringResource(R.string.add_notif_title_placeholder)) },
            isError = stateHolder.labelErrorResId != null,
            supportingText = {
                if (stateHolder.labelErrorResId != null) {
                    Text(
                        text = stringResource(id = stateHolder.labelErrorResId!!),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
        )

        TextField(
            value = stateHolder.text,
            onValueChange = stateHolder::onTextChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.add_notif_content_label)) },
            placeholder = { Text(text = stringResource(R.string.add_notif_content_placeholder)) },
        )

        ExposedDropdownMenuBox(
            expanded = stateHolder.expandedDropDownCatNotif,
            onExpandedChange = stateHolder::onDropDownExpandedChange,
            modifier = Modifier
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                singleLine = false,
                value = stateHolder.settingsNotif.selectedCategory.name,
                onValueChange = {},
                label = { Text(text = stringResource(R.string.add_notif_priority_label)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = stateHolder.expandedDropDownCatNotif
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = stateHolder.expandedDropDownCatNotif,
                onDismissRequest = { stateHolder.onDropDownExpandedChange(false) }
            ) {
                NotificationType.entries.forEach { notificationType ->
                    DropdownMenuItem(
                        text = { Text(notificationType.name) },
                        onClick = {
                            stateHolder.onNotificationTypeSelected(notificationType)
                        }
                    )
                }
            }
        }

        switchWithTextInBox(
            text = stringResource(R.string.add_notif_switch_expandable),
            checked = stateHolder.settingsNotif.isBigText,
            onChecked = stateHolder::onCheckedChangeBigText,
            enabled = stateHolder.text.isNotBlank()
        )

        switchWithTextInBox(
            text = stringResource(R.string.add_notif_switch_opens_activity),
            checked = stateHolder.settingsNotif.canOpened,
            onChecked = stateHolder::onCheckedChangeCanOpened
        )

        switchWithTextInBox(
            text = stringResource(R.string.add_notif_switch_reply_action),
            checked = stateHolder.settingsNotif.hasReply,
            onChecked = stateHolder::onCheckedChangeHasReply
        )

        Button(
            onClick = {
                stateHolder.onButtonClick()?.let { titleAndContentPair ->
                    onShowButtonClicked(
                        titleAndContentPair,
                        stateHolder.settingsNotif
                    )

                    stateHolder.clearFields()
                }
            }
        ) {
            Text(text = stringResource(R.string.add_notif_create_button))
        }
    }
}

@Composable
fun switchWithTextInBox(
    text: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            fontSize = FontSizes.large,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Switch(
            checked = checked,
            onCheckedChange = onChecked,
            enabled = enabled,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddNotifContentPreview() {
    val fakeState = AddNotifStateHolder().apply {
        label = "Test title"
        text = "Test text"
    }

    AddNotifContent(
        modifier = Modifier,
        stateHolder = fakeState,
        onShowButtonClicked = { _, _ -> }
    )
}

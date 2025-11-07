package com.soechka1.myfirstawesomeandroidproject.navScreens.addnotif


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.NotificationSettings
import com.soechka1.myfirstawesomeandroidproject.model.NotificationType


class AddNotifStateHolder {
    var label by mutableStateOf("")
    var labelErrorResId by mutableStateOf<Int?>(null)
    var text by mutableStateOf("")
    var expandedDropDownCatNotif by mutableStateOf(false)
    var settingsNotif by mutableStateOf(NotificationSettings())

    fun onLabelChange(newLabel: String) {
        label = newLabel
        labelErrorResId = if (label.isBlank()) {
            R.string.add_notif_title_error
        } else {
            null
        }
    }

    fun onTextChange(newText: String) {
        text = newText
        if (newText.isBlank()) {
            settingsNotif = settingsNotif.copy(isBigText = false)
        }
    }

    fun onDropDownExpandedChange(isExpanded: Boolean) {
        expandedDropDownCatNotif = isExpanded
    }

    fun onNotificationTypeSelected(type: NotificationType) {
        settingsNotif = settingsNotif.copy(selectedCategory = type)
        expandedDropDownCatNotif = false
    }

    fun onCheckedChangeBigText(checked: Boolean) {
        settingsNotif = settingsNotif.copy(isBigText = checked)
    }

    fun onCheckedChangeCanOpened(checked: Boolean) {
        settingsNotif = settingsNotif.copy(canOpened = checked)
    }

    fun onCheckedChangeHasReply(checked: Boolean) {
        settingsNotif = settingsNotif.copy(hasReply = checked)
    }

    fun onButtonClick(): Pair<String, String>? {
        if (label.isBlank()) {
            labelErrorResId = R.string.add_notif_title_error
            return null
        }
        return Pair(label, text)
    }

    fun clearFields() {
        label = ""
        text = ""
        labelErrorResId = null
        expandedDropDownCatNotif = false
        settingsNotif = NotificationSettings()
    }
}

@Composable
fun AddNotifScreen(
    modifier: Modifier,
    onShowButtonClicked: (Pair<String, String>, NotificationSettings) -> Unit
) {
    AddNotifContent(
        modifier = modifier,
        stateHolder = remember { AddNotifStateHolder() },
        onShowButtonClicked = onShowButtonClicked
    )
}

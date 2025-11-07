package com.soechka1.myfirstawesomeandroidproject.navScreens.editnotif

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


class EditNotifStateHolder {
    var notificationId by mutableStateOf("")
    var newText by mutableStateOf("")

    fun onIdChange(newId: String) {
        notificationId = newId
    }

    fun onTextChange(newText: String) {
        this.newText = newText
    }

    fun getUpdateData(): Pair<Int, String>? {
        return if (notificationId.isNotEmpty() && newText.isNotEmpty()) {
            Pair(notificationId.toIntOrNull() ?: return null, newText)
        } else null
    }

    fun clearFields() {
        notificationId = ""
        newText = ""
    }
}

@Composable
fun EditNotifScreen(modifier: Modifier, onUpdateClicked: (Int, String) -> Unit, onClearAllClicked: () -> Unit) {
    EditNotifContent(
        modifier = modifier,
        stateHolder = remember { EditNotifStateHolder() },
        onUpdateClicked = onUpdateClicked,
        onClearAllClicked = onClearAllClicked
    )
}


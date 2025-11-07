package com.soechka1.myfirstawesomeandroidproject.navScreens.editnotif

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens

@Composable
fun EditNotifContent(
    modifier: Modifier,
    stateHolder: EditNotifStateHolder,
    onUpdateClicked: (Int, String) -> Unit,
    onClearAllClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.small)
    ) {
        TextField(
            value = stateHolder.notificationId,
            onValueChange = stateHolder::onIdChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.edit_notif_id_label)) },
            placeholder = { Text(text = stringResource(R.string.edit_notif_id_placeholder)) },
            singleLine = true
        )

        TextField(
            value = stateHolder.newText,
            onValueChange = stateHolder::onTextChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.edit_notif_text_label)) },
            placeholder = { Text(text = stringResource(R.string.edit_notif_text_placeholder)) },
            singleLine = true
        )

        Button(onClick = {
            val data = stateHolder.getUpdateData()
            if (data != null) {
                onUpdateClicked(data.first, data.second)
                stateHolder.clearFields()
            }
        }) {
            Text(text = stringResource(R.string.edit_notif_update_button), modifier = Modifier.fillMaxWidth())
        }

        Button(onClick = { onClearAllClicked() }) {
            Text(text = stringResource(R.string.edit_notif_clear_button), modifier = Modifier.fillMaxWidth())
        }
    }
}

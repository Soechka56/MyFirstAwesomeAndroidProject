package com.soechka1.myfirstawesomeandroidproject.navscreens.profilepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.UserDataModel
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens

@Composable
fun ProfileScreen(
    user: UserDataModel,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.profile_delete_dialog_title)) },
            text = { Text(stringResource(R.string.profile_delete_dialog_text)) },
            confirmButton = {
                Button(onClick = {
                    showDeleteDialog = false
                    onDeleteAccountClick()
                }) { Text(stringResource(R.string.common_delete)) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.common_cancel))
                }
            }
        )
    }

    ProfileContent(
        user = user,
        onLogoutClick = onLogoutClick,
        onDeleteAccountClick = { showDeleteDialog = true },
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.medium)
    )
}

@Composable
fun DeletedAccountScreen(
    user: UserDataModel,
    onHardDeleteClick: () -> Unit,
    onRestoreClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showHardDeleteDialog by remember { mutableStateOf(false) }

    if (showHardDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showHardDeleteDialog = false },
            title = { Text(stringResource(R.string.deleted_hard_delete_dialog_title)) },
            text = { Text(stringResource(R.string.deleted_hard_delete_dialog_text)) },
            confirmButton = {
                Button(onClick = {
                    showHardDeleteDialog = false
                    onHardDeleteClick()
                }) { Text(stringResource(R.string.deleted_hard_delete)) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showHardDeleteDialog = false }) {
                    Text(stringResource(R.string.common_cancel))
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.medium),
        verticalArrangement = Arrangement.spacedBy(Dimens.medium)
    ) {
        Text(text = stringResource(R.string.deleted_title))
        Text(text = user.username)
        Text(text = user.email)
        Text(text = stringResource(R.string.deleted_text))

        Button(onClick = onRestoreClick) { Text(stringResource(R.string.deleted_restore)) }

        OutlinedButton(onClick = { showHardDeleteDialog = true }) {
            Text(stringResource(R.string.deleted_hard_delete))
        }

        OutlinedButton(onClick = onLogoutClick) {
            Text(stringResource(R.string.profile_logout))
        }
    }
}

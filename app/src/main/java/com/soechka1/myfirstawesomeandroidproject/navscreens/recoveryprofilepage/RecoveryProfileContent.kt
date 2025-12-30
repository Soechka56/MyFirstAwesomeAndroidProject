package com.soechka1.myfirstawesomeandroidproject.navscreens.recoveryprofilepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes

@Composable
fun RecoveryProfileContent(
    isRestoring: Boolean,
    daysSince: Long?,
    onRestoreClick: () -> Unit,
    onHardDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val daysAfterDelete = 7 // хз где хранить такое
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.medium),
        verticalArrangement = Arrangement.spacedBy(Dimens.medium)
    ) {
        Text(
            text = stringResource(R.string.recovery_profile_title),
            fontSize = FontSizes.titleMedium
        )

        Text(
            text = stringResource(R.string.deleted_text),
            fontSize = FontSizes.bodyMedium
        )

        daysSince?.let { days ->
            if (days < daysAfterDelete) {
                Text(
                    text = stringResource(R.string.deleted_text_days_left, daysAfterDelete - days),
                    fontSize = FontSizes.bodySmall
                )
            } else {
                Text(
                    text = stringResource(R.string.deleted_text_expired),
                    fontSize = FontSizes.bodySmall
                )
            }
        }

        Button(
            onClick = onRestoreClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isRestoring && (daysSince == null || daysSince < daysAfterDelete)
        ) {
            Text(
                text = if (isRestoring) {
                    stringResource(R.string.deleted_restore_in_progress)
                } else {
                    stringResource(R.string.deleted_restore)
                }
            )
        }

        OutlinedButton(
            onClick = onHardDeleteClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isRestoring
        ) {
            Text(stringResource(R.string.deleted_hard_delete))
        }

        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isRestoring
        ) {
            Text(stringResource(R.string.common_cancel))
        }
    }
}

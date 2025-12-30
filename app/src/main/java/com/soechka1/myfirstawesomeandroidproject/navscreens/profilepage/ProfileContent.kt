package com.soechka1.myfirstawesomeandroidproject.navscreens.profilepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.UserDataModel
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens

@Composable
fun ProfileContent(
    user: UserDataModel,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.small)
    ) {
        Text(text = stringResource(R.string.profile_title))
        Text(text = user.username)
        Text(text = stringResource(R.string.profile_email, user.email))
        Text(text = stringResource(R.string.profile_user_id, user.userId))
        Text(text = stringResource(R.string.profile_favorites_count, user.favorites.size))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) { Text(stringResource(R.string.profile_logout)) }

        OutlinedButton(
            onClick = onDeleteAccountClick,
            modifier = Modifier.fillMaxWidth()
        ) { Text(stringResource(R.string.profile_delete_account)) }
    }
}

package com.soechka1.myfirstawesomeandroidproject.navScreens.usermsg

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.MessagesObject
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens

@Composable
fun UserMsgContent(modifier: Modifier, stateHolder: UserMsgStateHolder) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = stateHolder.inputText,
                onValueChange = stateHolder::onInputChange,
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.user_msg_input_label)) },
                placeholder = { Text(stringResource(R.string.user_msg_input_placeholder)) },
                singleLine = true
            )
            Spacer(Modifier.width(Dimens.small))
            Button(
                onClick = { stateHolder.addMessage(stateHolder.inputText) },
                modifier = Modifier.height(Dimens.extraLarge)
            ) { Text(stringResource(R.string.user_msg_send_button)) }
        }

        Spacer(Modifier.height(Dimens.small))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.small)
        ) {
            items(MessagesObject.messages) { m ->
                Text(
                    text = m.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.small)
                )
            }
        }
    }
}


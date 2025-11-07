package com.soechka1.myfirstawesomeandroidproject.navScreens.usermsg


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.soechka1.myfirstawesomeandroidproject.model.MessageModel
import com.soechka1.myfirstawesomeandroidproject.model.MessagesObject


class UserMsgStateHolder {
    var inputText by mutableStateOf("")
    fun onInputChange(newText: String) {
        inputText = newText
    }

    fun addMessage(text: String) {
        if (text.isNotEmpty()) {
            MessagesObject.messages.add(MessageModel(text))
            inputText = ""
        }
    }

    fun addReplyMessage(text: String) {
        addMessage(text)
    }
}

@Composable
fun UserMsgScreen(modifier: Modifier) {
    UserMsgContent(modifier = modifier,
        stateHolder = remember { UserMsgStateHolder() })
}

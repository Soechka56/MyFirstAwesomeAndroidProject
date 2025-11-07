package com.soechka1.myfirstawesomeandroidproject.model

import androidx.compose.runtime.mutableStateListOf

object MessagesObject {
    val messages = mutableStateListOf<MessageModel>()

    fun addMessage(text: String) {
        val message = MessageModel(text = text)
        messages.add(message)
    }
}

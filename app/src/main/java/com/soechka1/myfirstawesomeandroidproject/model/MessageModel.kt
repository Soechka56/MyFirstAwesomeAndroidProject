package com.soechka1.myfirstawesomeandroidproject.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageModel(
    val text: String,
    // for correct sorting by time
    val timestamp: Long = System.currentTimeMillis()
)
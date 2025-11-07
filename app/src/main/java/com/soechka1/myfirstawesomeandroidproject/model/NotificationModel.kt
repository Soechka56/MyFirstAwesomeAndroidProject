package com.soechka1.myfirstawesomeandroidproject.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationModel(
    val id: Int,
    val title: String,
    val content: String? = null,
    val type: NotificationType = NotificationType.MEDIUM
)

@Serializable
data class NotificationSettings(
    val selectedCategory: NotificationType = NotificationType.MEDIUM,
    val isBigText: Boolean = false,
    val canOpened: Boolean = false,
    val hasReply: Boolean = false
)

@Serializable
enum class NotificationType {
    URGENT, HIGH, MEDIUM, LOW
}

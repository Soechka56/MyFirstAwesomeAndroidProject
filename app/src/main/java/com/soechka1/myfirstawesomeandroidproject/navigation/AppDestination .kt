package com.soechka1.myfirstawesomeandroidproject.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.soechka1.myfirstawesomeandroidproject.R

enum class AppDestination(@DrawableRes val icon: Int,
    @StringRes val label: Int) {
    AddNotif(
        icon = R.drawable.ic_add_notiif_24,
        label = R.string.add_notif_title,
    ),
    EditNotif(
        icon = R.drawable.ic_edit_notif_24,
        label = R.string.edit_notif_title,
    ),
    UserMsg(
        icon = R.drawable.ic_messages_24,
        label = R.string.user_msg_title,
    ),
}
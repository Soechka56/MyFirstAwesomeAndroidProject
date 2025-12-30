package com.soechka1.myfirstawesomeandroidproject.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.soechka1.myfirstawesomeandroidproject.R
import kotlinx.serialization.Serializable
@Serializable data object RecoveryPageObject
@Serializable data object SignPageObject
@Serializable data object LoginPageObject
@Serializable data object ProfilePageObject

@Serializable
data class ItemPageObject(
    val itemId: Int
)

enum class AppDestination(@DrawableRes val icon: Int,
                          @StringRes val label: Int) {
    FavoritePageObject(
        icon = R.drawable.ic_favorites_24,
        label = R.string.favorites_title,
    ),

    ContentPageObject(
        icon = R.drawable.ic_albums24,
        label = R.string.albums_title,
    ),

    NewItemPageObject(
        icon = R.drawable.ic_create_new_24,
        label = R.string.add_album_title,
    ),

    ProfileObject(
        icon = R.drawable.ic_account_24,
        label = R.string.profile_title,
    ),
}
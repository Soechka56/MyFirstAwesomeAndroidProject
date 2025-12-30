package com.soechka1.myfirstawesomeandroidproject.model

import com.soechka1.myfirstawesomeandroidproject.db.entity.FavoriteWithAlbums
data class UserDataModel(
    val userId: Long,
    var username: String,
    var email: String,
    var favorites: List<FavoriteWithAlbums> = emptyList()
)
package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithFavoriteList(
    @Embedded val user: User,
    // "join" по двум результам селект запроса, потом еще один вложенный
    // "join" который для фейворит ище альбомы, накладно, но для 1 пользователя некритично..
    @Relation(
        entity = Favorite::class,
        parentColumn = "userId",
        entityColumn = "userId"
    ) val favorites: List<FavoriteWithAlbums>
)
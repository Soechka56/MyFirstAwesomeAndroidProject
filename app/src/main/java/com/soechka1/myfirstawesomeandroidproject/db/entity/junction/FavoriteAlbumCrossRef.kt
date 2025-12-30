package com.soechka1.myfirstawesomeandroidproject.db.entity.junction

import androidx.room.Entity

@Entity(primaryKeys = ["favoriteId", "albumId"])
data class FavoriteAlbumCrossRef(
    val favoriteId: Long,
    val albumId: Long,
)
package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.soechka1.myfirstawesomeandroidproject.db.entity.junction.FavoriteAlbumCrossRef

data class FavoriteWithAlbums(
    @Embedded val favorite: Favorite,
    @Relation(
        parentColumn = "favoriteId",
        entityColumn = "albumId",
        associateBy = Junction(FavoriteAlbumCrossRef::class)
    )
    val albums: List<Album>
)
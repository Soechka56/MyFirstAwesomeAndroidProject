package com.soechka1.myfirstawesomeandroidproject.model

import com.soechka1.myfirstawesomeandroidproject.db.entity.Album
import com.soechka1.myfirstawesomeandroidproject.db.view.RatingAlbum

data class AlbumWithRating(
    val album: Album,
    val rating: RatingAlbum?,
    val coverFilePath: String?
)





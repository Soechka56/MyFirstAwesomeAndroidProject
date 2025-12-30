package com.soechka1.myfirstawesomeandroidproject.dao

import androidx.room.Dao
import androidx.room.Query
import com.soechka1.myfirstawesomeandroidproject.db.view.RatingAlbum

@Dao
interface RatingAlbumDao {

    @Query("SELECT * FROM rating_album_view WHERE album_id = :albumId")
    suspend fun getSummary(albumId: Long): RatingAlbum?
}

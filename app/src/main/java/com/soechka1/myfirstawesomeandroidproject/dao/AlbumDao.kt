package com.soechka1.myfirstawesomeandroidproject.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soechka1.myfirstawesomeandroidproject.db.entity.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM albums WHERE albumId = :albumId")
    suspend fun getAlbumById(albumId: Long): Album?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAlbum(album: Album): Long
}
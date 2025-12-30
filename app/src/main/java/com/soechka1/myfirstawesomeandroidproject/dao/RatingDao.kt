package com.soechka1.myfirstawesomeandroidproject.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soechka1.myfirstawesomeandroidproject.db.entity.Rating

@Dao
interface RatingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRating(rating: Rating): Long

    @Query("SELECT * FROM ratings WHERE album_id = :albumId AND user_rating_id = :userId LIMIT 1")
    suspend fun getRatingForUser(albumId: Long, userId: Long): Rating?
}

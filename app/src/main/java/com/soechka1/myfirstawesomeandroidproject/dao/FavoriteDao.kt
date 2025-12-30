package com.soechka1.myfirstawesomeandroidproject.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.soechka1.myfirstawesomeandroidproject.db.entity.Favorite
import com.soechka1.myfirstawesomeandroidproject.db.entity.junction.FavoriteAlbumCrossRef

@Dao
interface FavoriteDao {

    @Query("SELECT favoriteId FROM favorites WHERE userId = :userId LIMIT 1")
    suspend fun getFavoriteIdByUserId(userId: Long): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: FavoriteAlbumCrossRef)

    @Query("DELETE FROM FavoriteAlbumCrossRef WHERE favoriteId = :favoriteId AND albumId = :albumId")
    suspend fun deleteCrossRef(favoriteId: Long, albumId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteAlbumCrossRef WHERE favoriteId = :favoriteId AND albumId = :albumId)")
    suspend fun isFavorite(favoriteId: Long, albumId: Long): Boolean

    @Transaction
    suspend fun getOrCreateFavoriteId(userId: Long): Long? {
        val existingId = getFavoriteIdByUserId(userId)
        if (existingId != null) return existingId

        val insertedId = insertFavorite(Favorite(userId = userId))
        return if (insertedId != -1L) insertedId else getFavoriteIdByUserId(userId)
    }
}

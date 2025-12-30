package com.soechka1.myfirstawesomeandroidproject.repo

import com.soechka1.myfirstawesomeandroidproject.dao.RatingAlbumDao
import com.soechka1.myfirstawesomeandroidproject.dao.RatingDao
import com.soechka1.myfirstawesomeandroidproject.db.entity.Rating
import com.soechka1.myfirstawesomeandroidproject.db.view.RatingAlbum
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RatingRepository(
    private val ratingDao: RatingDao,
    private val ratingAlbumDao: RatingAlbumDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getSummary(albumId: Long): RatingAlbum? {
        return withContext(dispatcher) {
            ratingAlbumDao.getSummary(albumId)
        }
    }

    suspend fun getUserRating(albumId: Long, userId: Long): Rating? {
        return withContext(dispatcher) {
            ratingDao.getRatingForUser(albumId, userId)
        }
    }

    suspend fun saveRating(rating: Rating) {
        withContext(dispatcher) {
            ratingDao.upsertRating(rating)
        }
    }
}

package com.soechka1.myfirstawesomeandroidproject.repo

import com.soechka1.myfirstawesomeandroidproject.dao.FavoriteDao
import com.soechka1.myfirstawesomeandroidproject.dao.UserDao
import com.soechka1.myfirstawesomeandroidproject.db.entity.Album
import com.soechka1.myfirstawesomeandroidproject.db.entity.junction.FavoriteAlbumCrossRef
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FavoriteRepository(
    private val favoriteDao: FavoriteDao,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getFavoriteAlbums(userId: Long): List<Album> {
        return withContext(dispatcher) {
            val user = userDao.userWithFavoriteListById(userId)
            val favorites = user?.favorites ?: emptyList()
            val result = mutableListOf<Album>()

            for (favorite in favorites) {
                for (album in favorite.albums) {
                    if (result.none { it.albumId == album.albumId }) {
                        result.add(album)
                    }
                }
            }

            result
        }
    }

    suspend fun getFavoriteAlbumIds(userId: Long): Set<Long> {
        return withContext(dispatcher) {
            val user = userDao.userWithFavoriteListById(userId)
            val favorites = user?.favorites ?: emptyList()
            val ids = mutableSetOf<Long>()

            for (favorite in favorites) {
                for (album in favorite.albums) {
                    ids.add(album.albumId)
                }
            }

            ids
        }
    }

    suspend fun toggleFavorite(userId: Long, albumId: Long): Boolean {
        return withContext(dispatcher) {
            var isFavorite = false
            val favoriteId = favoriteDao.getOrCreateFavoriteId(userId)

            if (favoriteId != null) {
                val alreadyFavorite = favoriteDao.isFavorite(favoriteId, albumId)

                if (alreadyFavorite) {
                    favoriteDao.deleteCrossRef(favoriteId, albumId)
                    isFavorite = false
                } else {
                    favoriteDao.insertCrossRef(FavoriteAlbumCrossRef(favoriteId, albumId))
                    isFavorite = true
                }
            }

            isFavorite
        }
    }
}

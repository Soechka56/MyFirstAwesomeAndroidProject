package com.soechka1.myfirstawesomeandroidproject.repo

import android.content.Context
import com.soechka1.myfirstawesomeandroidproject.dao.AlbumDao
import com.soechka1.myfirstawesomeandroidproject.dao.FileDao
import com.soechka1.myfirstawesomeandroidproject.dao.RatingAlbumDao
import com.soechka1.myfirstawesomeandroidproject.dao.UserDao
import com.soechka1.myfirstawesomeandroidproject.db.entity.Album
import com.soechka1.myfirstawesomeandroidproject.model.AlbumWithRating
import com.soechka1.myfirstawesomeandroidproject.utils.FileUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AlbumRepository(
    private val albumDao: AlbumDao,
    private val ratingAlbumDao: RatingAlbumDao,
    private val fileDao: FileDao,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAlbumsWithRating(context: Context): List<AlbumWithRating> {
        return withContext(dispatcher) {
            val albums = albumDao.getAllAlbums()
            buildAlbumsWithRating(context, albums)
        }
    }

    suspend fun getAlbumsWithRating(context: Context, albums: List<Album>): List<AlbumWithRating> {
        return withContext(dispatcher) {
            buildAlbumsWithRating(context, albums)
        }
    }

    suspend fun getAlbumWithRating(context: Context, albumId: Long, userId: Long?): AlbumWithRating? {
        return withContext(dispatcher) {
            var album = albumDao.getAlbumById(albumId)

            if (album == null && userId != null) {
                val user = userDao.userWithFavoriteListById(userId)
                val favorites = user?.favorites ?: emptyList()
                for (favorite in favorites) {
                    for (favoriteAlbum in favorite.albums) {
                        if (favoriteAlbum.albumId == albumId) {
                            album = favoriteAlbum
                        }
                    }
                }
            }

            if (album == null) {
                null
            } else {
                val rating = ratingAlbumDao.getSummary(album.albumId)
                val coverPath = getCoverPath(context, album.coverFileId)
                AlbumWithRating(album, rating, coverPath)
            }
        }
    }

    suspend fun createAlbum(
        name: String,
        singerName: String,
        coverFileId: Long?
    ): Long {
        val album = Album(
            albumId = 0,
            name = name,
            singerName = singerName,
            coverFileId = coverFileId
        )

        return withContext(dispatcher) {
            albumDao.insertAlbum(album)
        }
    }

    private suspend fun buildAlbumsWithRating(
        context: Context,
        albums: List<Album>
    ): List<AlbumWithRating> {
        val result = mutableListOf<AlbumWithRating>()

        for (album in albums) {
            val rating = ratingAlbumDao.getSummary(album.albumId)
            val coverPath = getCoverPath(context, album.coverFileId)
            result.add(AlbumWithRating(album, rating, coverPath))
        }

        return result
    }

    private suspend fun getCoverPath(context: Context, coverFileId: Long?): String? {
        var result: String? = null
        val fileId = coverFileId

        if (fileId != null) {
            val fileEntity = fileDao.getFileById(fileId)
            if (fileEntity != null) {
                result = FileUtils.getFilePath(context, fileEntity.storageFileName)
            }
        }

        return result
    }
}

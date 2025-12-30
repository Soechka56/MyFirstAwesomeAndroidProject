package com.soechka1.myfirstawesomeandroidproject.di

import android.content.Context
import androidx.room.Room
import com.soechka1.myfirstawesomeandroidproject.db.ApplicationDatabase
import com.soechka1.myfirstawesomeandroidproject.mapper.UserModelMapper
import com.soechka1.myfirstawesomeandroidproject.repo.AlbumRepository
import com.soechka1.myfirstawesomeandroidproject.repo.FavoriteRepository
import com.soechka1.myfirstawesomeandroidproject.repo.FileRepository
import com.soechka1.myfirstawesomeandroidproject.repo.RatingRepository
import com.soechka1.myfirstawesomeandroidproject.repo.UserRepository
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private const val DB_NAME = "album_playlist.db"
    private var applicationDatabase: ApplicationDatabase? = null

    private val userModelMapper = UserModelMapper()

    private val userRepository by lazy {
        try{
            UserRepository(
                mapper = userModelMapper,
                dispatcher = Dispatchers.IO,
                userDao = getDatabase().userDao
            )
        } catch (e: Exception)
            { System.out.println("userRepository init failed!")
                throw e
            }
    }

    private val albumRepository by lazy {
        try {
            AlbumRepository(
                albumDao = getDatabase().albumDao,
                ratingAlbumDao = getDatabase().ratingAlbumDao,
                fileDao = getDatabase().fileDao,
                userDao = getDatabase().userDao,
                dispatcher = Dispatchers.IO
            )
        } catch (e: Exception) {
            System.out.println("albumRepository init failed!")
            throw e
        }
    }

    private val favoriteRepository by lazy {
        try {
            FavoriteRepository(
                favoriteDao = getDatabase().favoriteDao,
                userDao = getDatabase().userDao,
                dispatcher = Dispatchers.IO
            )
        } catch (e: Exception) {
            System.out.println("favoriteRepository init failed!")
            throw e
        }
    }

    private val ratingRepository by lazy {
        try {
            RatingRepository(
                ratingDao = getDatabase().ratingDao,
                ratingAlbumDao = getDatabase().ratingAlbumDao,
                dispatcher = Dispatchers.IO
            )
        } catch (e: Exception) {
            System.out.println("ratingRepository init failed!")
            throw e
        }
    }

    private val fileRepository by lazy {
        try {
            FileRepository(
                fileDao = getDatabase().fileDao,
                dispatcher = Dispatchers.IO
            )
        } catch (e: Exception) {
            System.out.println("fileRepository init failed!")
            throw e
        }
    }

    fun initDatabase(appCtx: Context){
        applicationDatabase =
            Room.databaseBuilder(appCtx, ApplicationDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    fun getDatabase(): ApplicationDatabase = applicationDatabase ?: throw IllegalStateException("db is not init")

    fun getUserRepo(): UserRepository = userRepository

    fun getAlbumRepo(): AlbumRepository = albumRepository

    fun getFavoriteRepo(): FavoriteRepository = favoriteRepository

    fun getRatingRepo(): RatingRepository = ratingRepository

    fun getFileRepo(): FileRepository = fileRepository
}

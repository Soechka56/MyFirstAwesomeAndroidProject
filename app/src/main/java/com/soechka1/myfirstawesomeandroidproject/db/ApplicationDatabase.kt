package com.soechka1.myfirstawesomeandroidproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soechka1.myfirstawesomeandroidproject.dao.AlbumDao
import com.soechka1.myfirstawesomeandroidproject.dao.FavoriteDao
import com.soechka1.myfirstawesomeandroidproject.dao.FileDao
import com.soechka1.myfirstawesomeandroidproject.dao.RatingDao
import com.soechka1.myfirstawesomeandroidproject.dao.RatingAlbumDao
import com.soechka1.myfirstawesomeandroidproject.dao.UserDao
import com.soechka1.myfirstawesomeandroidproject.db.entity.Album
import com.soechka1.myfirstawesomeandroidproject.db.entity.Favorite
import com.soechka1.myfirstawesomeandroidproject.db.entity.FileEntity
import com.soechka1.myfirstawesomeandroidproject.db.entity.Rating
import com.soechka1.myfirstawesomeandroidproject.db.entity.User
import com.soechka1.myfirstawesomeandroidproject.db.entity.junction.FavoriteAlbumCrossRef
import com.soechka1.myfirstawesomeandroidproject.db.view.RatingAlbum

@Database(
    entities = [
        Album::class,
        Favorite::class,
        FavoriteAlbumCrossRef::class,
        FileEntity::class,
        Rating::class,
        User::class],
    views = [RatingAlbum::class],
    version = 2

)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val albumDao: AlbumDao
    abstract val favoriteDao: FavoriteDao
    abstract val ratingDao: RatingDao
    abstract val ratingAlbumDao: RatingAlbumDao
    abstract val fileDao: FileDao

}

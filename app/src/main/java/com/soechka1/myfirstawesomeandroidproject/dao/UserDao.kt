package com.soechka1.myfirstawesomeandroidproject.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.soechka1.myfirstawesomeandroidproject.db.entity.User
import com.soechka1.myfirstawesomeandroidproject.db.entity.UserWithFavoriteList
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): User?

    @Insert(onConflict = ABORT)
    fun insertUser(user: User): Long


    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    fun isEmailExists(email: String): Boolean

    @Query("SELECT password FROM users WHERE email = :email")
    fun getPasswordHashByEmail(email: String): String?

    @Transaction // несколько запросов
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun userWithFavoriteListByEmail(email: String): UserWithFavoriteList?

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    fun userWithFavoriteListById(userId: Long): UserWithFavoriteList?

    @Query("SELECT userId FROM users WHERE user_token = :token")
    fun isTokenValid(token: String): Long?

    @Update
    fun updateUser(user: User)

    @Query("UPDATE users SET user_token = :token WHERE userId = :userId")
    suspend fun updateToken(userId: Long, token: String)

    @Query("UPDATE users SET is_deleted = 1, created_at = :deletedAt WHERE userId = :userId")
    suspend fun markAsDeleted(userId: Long, deletedAt: Long)

    @Query("UPDATE users SET is_deleted = 0 WHERE userId = :userId")
    suspend fun restoreUser(userId: Long)

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun hardDeleteUser(userId: Long)

    @Delete
    fun deleteUser(user: User)
}
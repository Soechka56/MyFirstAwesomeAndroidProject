package com.soechka1.myfirstawesomeandroidproject.repo

import com.soechka1.myfirstawesomeandroidproject.Keys
import com.soechka1.myfirstawesomeandroidproject.dao.UserDao
import com.soechka1.myfirstawesomeandroidproject.db.entity.User
import com.soechka1.myfirstawesomeandroidproject.mapper.UserModelMapper
import com.soechka1.myfirstawesomeandroidproject.model.UserDataModel
import com.soechka1.myfirstawesomeandroidproject.model.UserSignDataModel
import com.soechka1.myfirstawesomeandroidproject.utils.SecurityUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID

class UserRepository(
    private val userDao: UserDao,
    private val mapper: UserModelMapper,
    private val dispatcher: CoroutineDispatcher
) {


    fun saveUserSession(token: String){
        UserDataRepository.saveData(Keys.TOKEN, token)
    }

    fun logout() {
        UserDataRepository.clear()
    }

    suspend fun createNewUser(userSignData: UserSignDataModel): Long? {
        val securePassword = SecurityUtils.hashPassword(userSignData.password)

        val user = User(
            username = userSignData.username,
            email = userSignData.email,
            password = securePassword,
            userToken = UUID.randomUUID().toString(),
            createdAt = System.currentTimeMillis()
        )

        return try {
            withContext(dispatcher) {
                val userId = userDao.insertUser(user = user)
                saveUserSession(user.userToken)
                userId
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Keys.CURRENT_USER_EXIST
        }
    }

    suspend fun updateToken(userId: Long){
        val newToken = UUID.randomUUID().toString()

        try { userDao.updateToken(userId, newToken) } catch (e: Exception) { }
         saveUserSession(newToken)
    }


    suspend fun getUserByToken(): Long? {
        val token = UserDataRepository.getToken() ?: return null
        return withContext(dispatcher) {
            try { userDao.isTokenValid(token) } catch (e: Exception) { null }
        }
    }

    suspend fun findUserDataById(userId: Long): UserDataModel? {
        return withContext(dispatcher) {
            val user = userDao.userWithFavoriteListById(userId)
            if (user != null) mapper.map(user) else null
        }
    }

    suspend fun getUserHashByEmail(email: String): String? {
        return withContext(dispatcher) {
            userDao.getPasswordHashByEmail(email)
        }
    }

    suspend fun findUserDataByEmail(email: String): UserDataModel? {
        return withContext(dispatcher) {
            val user = userDao.userWithFavoriteListByEmail(email)
            if(user != null) mapper.map(user) else null
        }
    }

    suspend fun isEmailExists(email: String): Boolean {
        return withContext(dispatcher) {
            userDao.isEmailExists(email)
        }
    }

    fun checkPassword(rawPassword: String, hashPassword: String): Boolean {
        return SecurityUtils.checkPassword(rawPassword, hashPassword)
    }

    suspend fun markUserAsDeleted(userId: Long) {
        withContext(dispatcher) {
            userDao.markAsDeleted(userId, System.currentTimeMillis())
        }
    }

    suspend fun restoreUser(userId: Long) {
        withContext(dispatcher) {
            userDao.restoreUser(userId)
        }
    }

    suspend fun hardDeleteUser(userId: Long) {
        withContext(dispatcher) {
            userDao.hardDeleteUser(userId)
        }
    }

    suspend fun isUserDeleted(userId: Long): Boolean {

        return withContext(dispatcher) {

            val user = userDao.userWithFavoriteListById(userId)

            user?.user?.isDeleted ?: false
        }
    }

    suspend fun getDaysSinceDeletion(userId: Long): Long? {
        return withContext(dispatcher) {
            val user = userDao.userWithFavoriteListById(userId)

            if (user?.user?.isDeleted == true) {
                val deletedAt = user.user.createdAt // косяк, нужно было доп поле когда было удаление

                val daysSince = (System.currentTimeMillis() - deletedAt) / (1000 * 60 * 60 * 24)
                daysSince
            } else {
                null
            }
        }
    }
}

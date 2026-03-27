package com.example.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BattleLogDao {

    @Transaction
    @Query("SELECT * FROM battle_log ORDER BY id ASC")
    suspend fun getAll(): List<BattleLogWithPlayers>

    @Transaction
    @Query("SELECT * FROM battle_log WHERE hashtag = :hashtag ORDER BY id ASC")
    suspend fun getBattleLogsByHashtag(hashtag: String): List<BattleLogWithPlayers>

    @Transaction
    @Query("SELECT * FROM battle_log WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): BattleLogWithPlayers?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattleLogs(items: List<BattleLogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(items: List<BattleLogPlayerEntity>)

    @Query("DELETE FROM battle_log")
    suspend fun clearAll()
}

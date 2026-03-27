package com.example.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BattleLogEntity::class, BattleLogPlayerEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class BattleLogDatabase : RoomDatabase() {
    abstract fun battleLogDao(): BattleLogDao
}

package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    @ColumnInfo(name = "user_name") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "user_token") val userToken: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)

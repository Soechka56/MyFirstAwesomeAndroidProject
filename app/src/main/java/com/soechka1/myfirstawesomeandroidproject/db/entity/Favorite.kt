package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = [Index(value = ["userId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = CASCADE
        )
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val favoriteId: Long = 0,
    val userId: Long
)

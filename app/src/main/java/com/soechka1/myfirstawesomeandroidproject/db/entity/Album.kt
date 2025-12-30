package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
        entity = FileEntity::class,
        parentColumns = ["fileId"],
        childColumns = ["cover_file_id"],
        onDelete = CASCADE,
    )]
)
data class Album(
    @PrimaryKey(autoGenerate = true) val albumId: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "singer_name") val singerName: String,
    @ColumnInfo(name = "cover_file_id") val coverFileId: Long?,
)

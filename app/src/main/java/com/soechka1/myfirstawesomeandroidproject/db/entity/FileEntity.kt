package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class FileEntity(
    @PrimaryKey(autoGenerate = true) val fileId: Long = 0,
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "storage_file_name") val storageFileName: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "size") val size: Long,
)

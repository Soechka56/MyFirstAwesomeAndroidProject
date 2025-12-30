package com.soechka1.myfirstawesomeandroidproject.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soechka1.myfirstawesomeandroidproject.db.entity.FileEntity

@Dao
interface FileDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFile(file: FileEntity): Long
    
    @Query("SELECT * FROM files WHERE fileId = :fileId")
    suspend fun getFileById(fileId: Long): FileEntity?
    
    @Delete
    suspend fun deleteFile(file: FileEntity)
}

package com.soechka1.myfirstawesomeandroidproject.repo

import com.soechka1.myfirstawesomeandroidproject.dao.FileDao
import com.soechka1.myfirstawesomeandroidproject.db.entity.FileEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File

class FileRepository(
    private val fileDao: FileDao,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun saveFileFromPath(path: String): Long? {
        return withContext(dispatcher) {
            var fileId: Long? = null
            val file = File(path)

            if (file.exists()) {
                val fileEntity = FileEntity(
                    fileId = 0,
                    fileName = file.name,
                    storageFileName = file.name,
                    type = "image/jpeg",
                    size = file.length()
                )
                fileId = fileDao.insertFile(fileEntity)
            }

            fileId
        }
    }
}

package com.soechka1.myfirstawesomeandroidproject.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {
    private const val IMAGES_DIR = "album_covers"

    fun getImagesDirectory(context: Context): File {
        val dir = File(context.filesDir, IMAGES_DIR)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    suspend fun saveImageToInternalStorage(context: Context, uri: Uri, fileName: String): String{
        return withContext(Dispatchers.IO) {
            val imagesDir = getImagesDirectory(context)
            val file = File(imagesDir, fileName)

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            file.absolutePath
        }
    }

    
    suspend fun getFileName(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
        var fileName = "image_${System.currentTimeMillis()}.jpg"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            if (nameIndex >= 0) {
                fileName = cursor.getString(nameIndex)
            }
        }
        fileName
    }
    
    fun getFilePath(context: Context, storageFileName: String): String? {
        val file = File(getImagesDirectory(context), storageFileName)
        return if (file.exists()) file.absolutePath else null
    }

}


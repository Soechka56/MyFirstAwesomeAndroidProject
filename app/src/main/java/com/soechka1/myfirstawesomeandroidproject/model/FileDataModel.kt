package com.soechka1.myfirstawesomeandroidproject.model

data class FileDataModel(
    val id: Long = 0,
    val name: String,
    val storageName: String,
    val type: String,
    val size: Long
)

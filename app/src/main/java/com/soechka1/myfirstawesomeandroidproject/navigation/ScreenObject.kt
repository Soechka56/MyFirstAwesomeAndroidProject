package com.soechka1.myfirstawesomeandroidproject.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginPageObject

@Serializable
data class NotesPageDataObject(
    val email: String,
    val password: String
)

@Serializable
data object AddNotePageDataObject

package com.soechka1.myfirstawesomeandroidproject.model

import kotlinx.serialization.Serializable

@Serializable
data class AlbumDataModel (
    val name: String,
    val singerName: String,
    val coverUrl: String,
// rating:
    val rhymes_images: Double,
    val structureRhythm: Double,
    val personalityCharisma: Double,
    val atmosphere_vibe: Double,
)
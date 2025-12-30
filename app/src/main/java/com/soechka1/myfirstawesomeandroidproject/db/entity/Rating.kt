package com.soechka1.myfirstawesomeandroidproject.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ratings",
    indices = [Index(value = ["album_id", "user_rating_id"], unique = true)], // уникальность, не больше 1 оценки на альбом
    foreignKeys = [

        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["user_rating_id"],
            onDelete = CASCADE,
        ),

        ForeignKey(
            entity = Album::class,
            parentColumns = ["albumId"],
            childColumns = ["album_id"],
            onDelete = CASCADE,
        )
    ]
)
data class Rating(
    @PrimaryKey(autoGenerate = true) val ratingId: Long = 0,
    @ColumnInfo(name = "album_id") val albumId: Long,
    @ColumnInfo(name = "user_rating_id") val userRatingId: Long,
    @ColumnInfo(name = "rhymes_images") val rhymesImages: Int, //Рифмы / образы 8.6
    @ColumnInfo(name = "structure_rhythm") val structureRhythm: Int, //Структура / ритмика 8
    @ColumnInfo(name = "personality_charisma") val personalityCharisma: Int, //Индивидуальность / харизма 9
    @ColumnInfo(name = "atmosphere_vibe") val atmosphereVibe: Int //Атмосфера / вайб 9
)





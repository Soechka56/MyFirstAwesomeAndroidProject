package com.soechka1.myfirstawesomeandroidproject.db.view

import androidx.room.DatabaseView
import androidx.room.ColumnInfo

@DatabaseView(
    viewName = "rating_album_view",
    value = """
        SELECT
            a.albumId AS album_id,
            COUNT(r.ratingId) AS ratings_count,
            
            AVG(r.rhymes_images) AS avg_rhymes_images,
            AVG(r.structure_rhythm) AS avg_structure_rhythm,
            AVG(r.personality_charisma) AS avg_personality_charisma,
            AVG(r.atmosphere_vibe) AS avg_atmosphere_vibe,
            
            AVG((r.rhymes_images + r.structure_rhythm + r.personality_charisma + r.atmosphere_vibe) / 4.0) AS avg_overall
        FROM albums a
        LEFT JOIN ratings r ON r.album_id = a.albumId
        GROUP BY a.albumId
    """
)
data class RatingAlbum(
    @ColumnInfo(name = "album_id") val albumId: Long,
    @ColumnInfo(name = "ratings_count") val ratingsCount: Int,
    @ColumnInfo(name = "avg_rhymes_images") val avgRhymesImages: Double? = 0.0,
    @ColumnInfo(name = "avg_structure_rhythm") val avgStructureRhythm: Double? = 0.0,
    @ColumnInfo(name = "avg_personality_charisma") val avgPersonalityCharisma: Double? = 0.0,
    @ColumnInfo(name = "avg_atmosphere_vibe") val avgAtmosphereVibe: Double? = 0.0,
    @ColumnInfo(name = "avg_overall") val avgOverall: Double? = 0.0
)

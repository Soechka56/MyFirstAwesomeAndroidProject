package com.soechka1.myfirstawesomeandroidproject.navscreens.itempage

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.db.entity.Album
import com.soechka1.myfirstawesomeandroidproject.db.entity.Rating
import com.soechka1.myfirstawesomeandroidproject.db.view.RatingAlbum
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ItemScreen(
    itemId: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val albumRepository = ServiceLocator.getAlbumRepo()
    val ratingRepository = ServiceLocator.getRatingRepo()

    var album by remember { mutableStateOf<Album?>(null) }
    var rating by remember { mutableStateOf<RatingAlbum?>(null) }
    var coverFilePath by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var userId by remember { mutableStateOf<Long?>(null) }

    var showRatingDialog by remember { mutableStateOf(false) }
    var ratingRhymes by remember { mutableStateOf(5f) }
    var ratingStructure by remember { mutableStateOf(5f) }
    var ratingPersonality by remember { mutableStateOf(5f) }
    var ratingAtmosphere by remember { mutableStateOf(5f) }

    LaunchedEffect(itemId) {
        scope.launch {
            isLoading = true

            val currentUserId = ServiceLocator.getUserRepo().getUserByToken()
            userId = currentUserId

            val albumWithRating = albumRepository.getAlbumWithRating(context, itemId.toLong(), currentUserId)
            album = albumWithRating?.album
            rating = albumWithRating?.rating
            coverFilePath = albumWithRating?.coverFilePath

            isLoading = false
        }
    }

    ItemContent(
        album = album,
        rating = rating,
        coverFilePath = coverFilePath,
        isLoading = isLoading,
        onBackClick = { navController.popBackStack() },
        showRatingDialog = showRatingDialog,
        onShowRatingDialog = {
            scope.launch {
                val currentUserId = userId
                val currentAlbum = album

                if (currentUserId != null && currentAlbum != null) {
                    val currentRating = ratingRepository.getUserRating(currentAlbum.albumId, currentUserId)

                    ratingRhymes = (currentRating?.rhymesImages?.toFloat() ?: 5f).coerceIn(1f, 10f)
                    ratingStructure = (currentRating?.structureRhythm?.toFloat() ?: 5f).coerceIn(1f, 10f)
                    ratingPersonality = (currentRating?.personalityCharisma?.toFloat() ?: 5f).coerceIn(1f, 10f)
                    ratingAtmosphere = (currentRating?.atmosphereVibe?.toFloat() ?: 5f).coerceIn(1f, 10f)
                    showRatingDialog = true
                }
            }
        },
        onDismissRatingDialog = { showRatingDialog = false },
        ratingRhymes = ratingRhymes,
        onRatingRhymesChange = { ratingRhymes = it },
        ratingStructure = ratingStructure,
        onRatingStructureChange = { ratingStructure = it },
        ratingPersonality = ratingPersonality,
        onRatingPersonalityChange = { ratingPersonality = it },
        ratingAtmosphere = ratingAtmosphere,
        onRatingAtmosphereChange = { ratingAtmosphere = it },
        onSaveRating = {
            scope.launch {
                val currentUserId = userId
                val currentAlbum = album

                if (currentUserId != null && currentAlbum != null) {
                    val ratingEntity = Rating(
                        albumId = currentAlbum.albumId,
                        userRatingId = currentUserId,
                        rhymesImages = ratingRhymes.roundToInt().coerceIn(1, 10),
                        structureRhythm = ratingStructure.roundToInt().coerceIn(1, 10),
                        personalityCharisma = ratingPersonality.roundToInt().coerceIn(1, 10),
                        atmosphereVibe = ratingAtmosphere.roundToInt().coerceIn(1, 10)
                    )

                    ratingRepository.saveRating(ratingEntity)
                    rating = ratingRepository.getSummary(currentAlbum.albumId)
                    showRatingDialog = false
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small)
    )
}

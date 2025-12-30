package com.soechka1.myfirstawesomeandroidproject.navscreens.itempage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.db.entity.Album
import com.soechka1.myfirstawesomeandroidproject.db.view.RatingAlbum
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes
import java.io.File

@Composable
fun ItemContent(
    album: Album?,
    rating: RatingAlbum?,
    coverFilePath: String?,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    showRatingDialog: Boolean,
    onShowRatingDialog: () -> Unit,
    onDismissRatingDialog: () -> Unit,
    ratingRhymes: Float,
    onRatingRhymesChange: (Float) -> Unit,
    ratingStructure: Float,
    onRatingStructureChange: (Float) -> Unit,
    ratingPersonality: Float,
    onRatingPersonalityChange: (Float) -> Unit,
    ratingAtmosphere: Float,
    onRatingAtmosphereChange: (Float) -> Unit,
    onSaveRating: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (showRatingDialog) {
        AlertDialog(
            onDismissRequest = onDismissRatingDialog,
            title = { Text(stringResource(R.string.rating_dialog_title)) },
            confirmButton = {
                TextButton(onClick = onSaveRating) {
                    Text(stringResource(R.string.rating_dialog_save))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRatingDialog) {
                    Text(stringResource(R.string.common_cancel))
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.small)
                ) {
                    Text(
                        text = stringResource(
                            R.string.rating_slider_rhymes_images,
                            ratingRhymes.toInt()
                        )
                    )


                    Slider(
                        value = ratingRhymes,
                        onValueChange = onRatingRhymesChange,
                        valueRange = 1f..10f,
                        steps = 8
                    )

                    Text(
                        text = stringResource(
                            R.string.rating_slider_structure_rhythm,
                            ratingStructure.toInt()
                        )
                    )
                    Slider(
                        value = ratingStructure,
                        onValueChange = onRatingStructureChange,
                        valueRange = 1f..10f,
                        steps = 8
                    )

                    Text(
                        text = stringResource(
                            R.string.rating_slider_personality_charisma,
                            ratingPersonality.toInt()
                        )
                    )
                    Slider(
                        value = ratingPersonality,
                        onValueChange = onRatingPersonalityChange,
                        valueRange = 1f..10f,
                        steps = 8
                    )

                    Text(
                        text = stringResource(
                            R.string.rating_slider_atmosphere_vibe,
                            ratingAtmosphere.toInt()
                        )
                    )
                    Slider(
                        value = ratingAtmosphere,
                        onValueChange = onRatingAtmosphereChange,
                        valueRange = 1f..10f,
                        steps = 8
                    )
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.medium),
        verticalArrangement = Arrangement.spacedBy(Dimens.medium)
    ) {
        Text(
            text = stringResource(R.string.album_details_title),
            fontSize = FontSizes.titleMedium
        )

        Text(
            text = "Back",
            modifier = Modifier.clickable { onBackClick() },
            fontSize = FontSizes.bodySmall,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else if (album == null) {
            Text(
                text = stringResource(R.string.album_not_found),
                fontSize = FontSizes.bodyMedium
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.medium)
            ) {
                if (coverFilePath != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(File(coverFilePath))
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.album_cover_content_description),
                        modifier = Modifier.size(Dimens.imageDp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = stringResource(R.string.album_cover_placeholder),
                        fontSize = FontSizes.caption,
                        color = Color.Gray
                    )
                }

                Text(
                    text = album.name,
                    fontSize = FontSizes.titleLarge
                )

                Text(
                    text = album.singerName,
                    fontSize = FontSizes.titleMedium
                )

                Text(
                    text = stringResource(R.string.album_rate_action),
                    modifier = Modifier.clickable { onShowRatingDialog() },
                    fontSize = FontSizes.bodyMedium,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )

                if (rating != null) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimens.extraSmall)
                    ) {
                        Text(
                            text = stringResource(R.string.rating_section_title),
                            fontSize = FontSizes.titleMedium
                        )

                        if (rating.ratingsCount > 0) {
                            Text(
                                text = stringResource(R.string.rating_count, rating.ratingsCount),
                                fontSize = FontSizes.bodyMedium
                            )
                            Text(
                                text = stringResource(
                                    R.string.rating_rhymes_images,
                                    rating.avgRhymesImages ?: 0.0
                                ),
                                fontSize = FontSizes.bodySmall
                            )


                            Text(
                                text = stringResource(
                                    R.string.rating_structure_rhythm,
                                    rating.avgStructureRhythm ?: 0.0
                                ),
                                fontSize = FontSizes.bodySmall
                            )

                            Text(
                                text = stringResource(
                                    R.string.rating_personality_charisma,
                                    rating.avgPersonalityCharisma ?: 0.0
                                ),
                                fontSize = FontSizes.bodySmall
                            )
                            Text(
                                text = stringResource(
                                    R.string.rating_atmosphere_vibe,
                                    rating.avgAtmosphereVibe ?: 0.0
                                ),
                                fontSize = FontSizes.bodySmall
                            )
                            Text(
                                text = stringResource(
                                    R.string.rating_overall,
                                    rating.avgOverall ?: 0.0
                                ),
                                fontSize = FontSizes.bodyLarge
                            )
                        }
                        else {
                            Text(
                                text = stringResource(R.string.rating_empty),
                                fontSize = FontSizes.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

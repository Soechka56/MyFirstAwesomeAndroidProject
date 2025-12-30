package com.soechka1.myfirstawesomeandroidproject.navscreens.favoritepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.soechka1.myfirstawesomeandroidproject.model.AlbumWithRating
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes
import java.io.File

@Composable
fun FavoriteContent(
    albumsWithRating: List<AlbumWithRating>,
    favoriteAlbumIds: Set<Long>,
    isLoading: Boolean,
    onAlbumClick: (AlbumWithRating) -> Unit,
    onFavoriteClick: (AlbumWithRating) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.medium),
        verticalArrangement = Arrangement.spacedBy(Dimens.medium)
    ) {
        Text(
            text = stringResource(R.string.favorites_title),
            fontSize = FontSizes.titleMedium
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else if (albumsWithRating.isEmpty()) {
            Text(
                text = stringResource(R.string.favorite_empty_list),
                fontSize = FontSizes.bodyMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = Dimens.small),
                verticalArrangement = Arrangement.spacedBy(Dimens.small)
            ) {
                items(albumsWithRating) { albumWithRating ->
                    val isFavorite = favoriteAlbumIds.contains(albumWithRating.album.albumId)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAlbumClick(albumWithRating) }
                    ) {
                        Column(
                            modifier = Modifier.padding(Dimens.medium),
                            verticalArrangement = Arrangement.spacedBy(Dimens.small)
                        ) {
                            if (albumWithRating.coverFilePath != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(File(albumWithRating.coverFilePath))
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
                                text = albumWithRating.album.name,
                                fontSize = FontSizes.titleMedium
                            )

                            Text(
                                text = albumWithRating.album.singerName,
                                fontSize = FontSizes.bodyMedium
                            )

                            albumWithRating.rating?.let { rating ->
                                if (rating.ratingsCount > 0) {
                                    Text(
                                        text = stringResource(
                                            R.string.album_rating_short,
                                            rating.avgOverall ?: 0.0,
                                            rating.ratingsCount
                                        ),
                                        fontSize = FontSizes.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            if (albumWithRating.rating == null || albumWithRating.rating.ratingsCount == 0) {
                                Text(
                                    text = stringResource(R.string.album_rate_action),
                                    modifier = Modifier.clickable { onAlbumClick(albumWithRating) },
                                    fontSize = FontSizes.bodySmall,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.Underline
                                )
                            }

                            Text(
                                text = stringResource(
                                    if (isFavorite) {
                                        R.string.favorite_remove_action
                                    } else {
                                        R.string.favorite_add_action
                                    }
                                ),
                                modifier = Modifier.clickable { onFavoriteClick(albumWithRating) },
                                fontSize = FontSizes.bodySmall,
                                color = if (isFavorite) Color.Red else Color.Blue,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }
            }
        }
    }
}

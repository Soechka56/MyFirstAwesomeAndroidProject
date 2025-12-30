package com.soechka1.myfirstawesomeandroidproject.navscreens.contentpage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.AlbumWithRating
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes
import java.io.File

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ContentContent(
    albumsWithRating: List<AlbumWithRating>,
    favoriteAlbumIds: Set<Long>,
    isLoading: Boolean,
    sortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit,
    onAlbumClick: (AlbumWithRating) -> Unit,
    onFavoriteClick: (AlbumWithRating) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.small),
        verticalArrangement = Arrangement.spacedBy(Dimens.extraSmall)
    ) {

        Text(
            text = stringResource(R.string.albums_title),
            fontSize = FontSizes.titleMedium
        )

        Text(
            text = stringResource(sortOption.labelRes),
            fontSize = FontSizes.bodySmall,
            color = Color.Gray
        )

        Text(
            text = stringResource(R.string.content_sort_action),
            modifier = Modifier.clickable { showBottomSheet = true },
            fontSize = FontSizes.bodyMedium,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.medium),
                    verticalArrangement = Arrangement.spacedBy(Dimens.small)
                ) {
                    Text(
                        text = stringResource(R.string.content_sort_title),
                        fontSize = FontSizes.titleMedium
                    )

                    SortOption.entries.forEach { option ->
                        val isSelected = option == sortOption
                        Text(
                            text = stringResource(option.labelRes),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSortOptionChange(option)
                                    showBottomSheet = false
                                }
                                .padding(Dimens.small),
                            fontSize = FontSizes.bodyMedium,
                            color = if (isSelected) Color.Blue else Color.Unspecified,
                            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None
                        )
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else if (albumsWithRating.isEmpty()) {
            Text(
                text = stringResource(R.string.content_empty_list),
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
                                color = if (isFavorite) { Color.Red } else { Color.Blue },
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }
            }
        }
    }
}

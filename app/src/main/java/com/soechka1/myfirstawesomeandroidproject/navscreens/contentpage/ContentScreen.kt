package com.soechka1.myfirstawesomeandroidproject.navscreens.contentpage

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
import com.soechka1.myfirstawesomeandroidproject.model.AlbumWithRating
import com.soechka1.myfirstawesomeandroidproject.navigation.ItemPageObject
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import kotlinx.coroutines.launch

@Composable
fun ContentScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val albumRepository = ServiceLocator.getAlbumRepo()
    val favoriteRepository = ServiceLocator.getFavoriteRepo()

    var albumsWithRating by remember { mutableStateOf<List<AlbumWithRating>>(emptyList()) }
    var sortedAlbums by remember { mutableStateOf<List<AlbumWithRating>>(emptyList()) }
    var favoriteAlbumIds by remember { mutableStateOf<Set<Long>>(emptySet()) }

    var userId by remember { mutableStateOf<Long?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var sortOption by remember { mutableStateOf(SortOption.NAME_ASC) }

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true

            val currentUserId = ServiceLocator.getUserRepo().getUserByToken()
            userId = currentUserId

            favoriteAlbumIds = if (currentUserId != null) {
                favoriteRepository.getFavoriteAlbumIds(currentUserId)
            } else {
                emptySet()
            }

            albumsWithRating = albumRepository.getAlbumsWithRating(context)
            sortedAlbums = sortAlbumsWithRating(albumsWithRating, sortOption)

            isLoading = false
        }
    }

    LaunchedEffect(sortOption, albumsWithRating) {
        sortedAlbums = sortAlbumsWithRating(albumsWithRating, sortOption)
    }

    ContentContent(
        albumsWithRating = sortedAlbums,
        favoriteAlbumIds = favoriteAlbumIds,
        isLoading = isLoading,
        sortOption = sortOption,
        onSortOptionChange = { sortOption = it },
        onAlbumClick = { albumWithRating ->
            navController.navigate(ItemPageObject(albumWithRating.album.albumId.toInt()))
        },
        onFavoriteClick = { albumWithRating ->
            scope.launch {
                val currentUserId = userId
                val albumId = albumWithRating.album.albumId

                if (currentUserId != null) {
                    val isFavorite = favoriteRepository.toggleFavorite(currentUserId, albumId)
                    favoriteAlbumIds = favoriteAlbumIds.toMutableSet().apply {
                        if (isFavorite) add(albumId) else remove(albumId)
                    }
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small)
    )
}

private fun sortAlbumsWithRating(
    albums: List<AlbumWithRating>,
    sortOption: SortOption
): List<AlbumWithRating> {
    return when (sortOption) {
        SortOption.NAME_ASC -> albums.sortedBy{ it.album.name }
        SortOption.NAME_DESC -> albums.sortedByDescending { it.album.name }
        SortOption.SINGER_ASC -> albums.sortedBy { it.album.singerName }
        SortOption.SINGER_DESC -> albums.sortedByDescending { it.album.singerName }
    }
}

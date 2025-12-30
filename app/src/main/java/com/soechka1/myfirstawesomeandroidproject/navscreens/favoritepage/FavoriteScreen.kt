package com.soechka1.myfirstawesomeandroidproject.navscreens.favoritepage

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
fun FavoriteScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val albumRepository = ServiceLocator.getAlbumRepo()
    val favoriteRepository = ServiceLocator.getFavoriteRepo()

    var albumsWithRating by remember { mutableStateOf<List<AlbumWithRating>>(emptyList()) }
    var favoriteAlbumIds by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var userId by remember { mutableStateOf<Long?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true

            val currentUserId = ServiceLocator.getUserRepo().getUserByToken()
            userId = currentUserId

            if (currentUserId != null) {
                val favoriteAlbums = favoriteRepository.getFavoriteAlbums(currentUserId)
                favoriteAlbumIds = favoriteAlbums.map { it.albumId }.toSet()
                albumsWithRating = albumRepository.getAlbumsWithRating(context, favoriteAlbums)
            } else {
                favoriteAlbumIds = emptySet()
                albumsWithRating = emptyList()
            }

            isLoading = false
        }
    }

    FavoriteContent(
        albumsWithRating = albumsWithRating,
        favoriteAlbumIds = favoriteAlbumIds,
        isLoading = isLoading,
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

                    if (!isFavorite) {
                        albumsWithRating = albumsWithRating.filterNot { it.album.albumId == albumId }
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

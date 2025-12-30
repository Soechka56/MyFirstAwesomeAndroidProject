package com.soechka1.myfirstawesomeandroidproject.navscreens.newitempage

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soechka1.myfirstawesomeandroidproject.TypeError
import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.navigation.AppDestination
import com.soechka1.myfirstawesomeandroidproject.repo.AlbumRepository
import com.soechka1.myfirstawesomeandroidproject.repo.FileRepository
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.utils.FileUtils
import kotlinx.coroutines.launch

@Composable
fun NewItemScreen(
    navController: NavController,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val albumRepository = ServiceLocator.getAlbumRepo()
    val fileRepository = ServiceLocator.getFileRepo()

    var albumName by remember { mutableStateOf("") }
    var albumNameError by remember { mutableStateOf<TypeError?>(null) }

    var singerName by remember { mutableStateOf("") }
    var singerNameError by remember { mutableStateOf<TypeError?>(null) }

    var selectedImagePath by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch {
                isLoading = true
                try {
                    val fileName = FileUtils.getFileName(context, uri)
                    val storageFileName = "cover_${System.currentTimeMillis()}_$fileName"
                    selectedImagePath = FileUtils.saveImageToInternalStorage(context, uri, storageFileName)
                } catch (e: Exception) {
                }
                isLoading = false
            }
        }
    }

    NewItemContent(
        albumName = albumName,
        onAlbumNameChange = { albumName = it; albumNameError = null },
        albumNameError = albumNameError,

        singerName = singerName,
        onSingerNameChange = { singerName = it; singerNameError = null },
        singerNameError = singerNameError,

        selectedImagePath = selectedImagePath,
        onSelectImageClick = { imagePickerLauncher.launch("image/*") },

        isLoading = isLoading,

        onSaveClick = {
            scope.launch {
                validateAndSave(
                    albumName = albumName,
                    singerName = singerName,
                    selectedImagePath = selectedImagePath,
                    albumRepository = albumRepository,
                    fileRepository = fileRepository,
                    navController = navController,
                    onAlbumNameError = { albumNameError = it },
                    onSingerNameError = { singerNameError = it },
                    onSuccess = {
                        onSuccess()
                    }
                )
            }
        },

        onCancelClick = {
            navController.popBackStack()
        },

        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small)
    )
}

private suspend fun validateAndSave(
    albumName: String,
    singerName: String,
    selectedImagePath: String?,
    albumRepository: AlbumRepository,
    fileRepository: FileRepository,
    navController: NavController,
    onAlbumNameError: (TypeError?) -> Unit,
    onSingerNameError: (TypeError?) -> Unit,
    onSuccess: () -> Unit
) {
    val albumNameError = if (albumName.isBlank()) {
        TypeError.ERROR_EMAIL_EMPTY
    } else null

    val singerNameError = if (singerName.isBlank()) {
        TypeError.ERROR_EMAIL_EMPTY
    } else null

    onAlbumNameError(albumNameError)
    onSingerNameError(singerNameError)

    if (albumNameError == null && singerNameError == null) {
        val fileId = if (selectedImagePath != null) {
            fileRepository.saveFileFromPath(selectedImagePath)
        } else {
            null
        }

        albumRepository.createAlbum(
            name = albumName,
            singerName = singerName,
            coverFileId = fileId
        )

        onSuccess()

        navController.navigate(AppDestination.ContentPageObject.name) {
            popUpTo(AppDestination.NewItemPageObject.name) { inclusive = true }
        }
    }
}

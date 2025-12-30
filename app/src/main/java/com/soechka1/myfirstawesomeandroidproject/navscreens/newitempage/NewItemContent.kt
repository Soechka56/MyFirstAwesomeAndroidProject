package com.soechka1.myfirstawesomeandroidproject.navscreens.newitempage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.TypeError
import com.soechka1.myfirstawesomeandroidproject.ui.components.CustomTextField
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes
import java.io.File

@Composable
fun NewItemContent(
    albumName: String,
    onAlbumNameChange: (String) -> Unit,
    albumNameError: TypeError?,

    singerName: String,
    onSingerNameChange: (String) -> Unit,
    singerNameError: TypeError?,

    selectedImagePath: String?,
    onSelectImageClick: () -> Unit,
    isLoading: Boolean,

    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.medium),
        verticalArrangement = Arrangement.spacedBy(Dimens.small)
    ) {
        Text(
            text = stringResource(R.string.add_album_title),
            fontSize = FontSizes.titleMedium
        )

        Column(
            modifier = Modifier
                .size(180.dp)
                .clickable { onSelectImageClick() }
                .padding(Dimens.small),
            verticalArrangement = Arrangement.spacedBy(Dimens.small)
        ) {
            if (isLoading) {
                // CircularProgressIndicator() как вариант вместо шиммера
            } else if (selectedImagePath != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(File(selectedImagePath))
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.album_cover_content_description),
                    modifier = Modifier.size(160.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = stringResource(R.string.new_item_cover_placeholder),
                    fontSize = FontSizes.bodySmall
                )
            }
        }

        CustomTextField(
            value = albumName,
            onValueChange = onAlbumNameChange,
            error = albumNameError,
            label = stringResource(R.string.new_item_album_name_label),
            keyboardType = KeyboardType.Text,
            modifier = Modifier
        )

        CustomTextField(
            value = singerName,
            onValueChange = onSingerNameChange,
            error = singerNameError,
            label = stringResource(R.string.new_item_singer_name_label),
            keyboardType = KeyboardType.Text,
            modifier = Modifier
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.small)
        ) {
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.new_item_save))
            }

            OutlinedButton(
                onClick = onCancelClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.common_cancel))
            }
        }
    }
}

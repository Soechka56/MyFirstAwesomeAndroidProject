package com.soechka1.myfirstawesomeandroidproject.navScreens.addnotepage

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.NoteDataModel
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens

@Composable
fun AddNoteScreen(
    navController: NavController,
    onNoteSave: (NoteDataModel) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf("") }

    var text by remember { mutableStateOf("") }
    val errorMessage = stringResource(R.string.add_note_error_title_empty)


    AddNoteContent(
        title = title,
        onTitleChange = { titleEdit -> title = titleEdit; titleError = "" },

        text = text,
        onTextChange = { textEdit -> text = textEdit },


        onButtonClick = {
            if (title.isEmpty()) {
                titleError = errorMessage
            } else {
                onNoteSave(
                    NoteDataModel(
                        title = title, text = text
                    )
                )
                navController.popBackStack()
            }

        },
        titleError = titleError,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small),
    )
}
package com.soechka1.myfirstawesomeandroidproject.navScreens.notespage

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
import androidx.navigation.NavController
import com.soechka1.myfirstawesomeandroidproject.model.NoteDataModel
import com.soechka1.myfirstawesomeandroidproject.navigation.AddNotePageDataObject
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens

@Composable
fun NotesScreen(
    navController: NavController,
    email: String,
    notes: List<NoteDataModel>,
    onThemeChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    NotesContent(
        email = email,
        notes = notes,
        onButtonClick = {
            navController.navigate(route = AddNotePageDataObject)
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        onButtonDropDownClick = { idx -> onThemeChange(idx) },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.extraSmall),
    )
}
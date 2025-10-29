package com.soechka1.myfirstawesomeandroidproject.navScreens.addnotepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.ui.components.CustomTextField
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens

@Composable
fun AddNoteContent(
    title: String,
    onTitleChange: (String) -> Unit,

    text: String,
    onTextChange: (String) -> Unit,

    onButtonClick: () -> Unit,
    titleError: String,
    modifier: Modifier
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth(),
            ) { Text(text = stringResource(R.string.add_note_button_save)) }
        },

        modifier = modifier // parent-modifier from presenter

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                CustomTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    errorText = titleError,
                    label = stringResource(R.string.add_note_label_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.medium)
                )

                CustomTextField(
                    value = text,
                    onValueChange = onTextChange,
                    label = stringResource(R.string.add_note_label_text),
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.medium)
                )
            }
        }
    }
}
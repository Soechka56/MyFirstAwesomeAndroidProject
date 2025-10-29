package com.soechka1.myfirstawesomeandroidproject.navScreens.notespage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.model.NoteDataModel
import com.soechka1.myfirstawesomeandroidproject.ui.AppTheme
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.FontSizes

@Composable
fun NotesContent(
    email: String,
    notes: List<NoteDataModel>,
    onButtonClick: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onButtonDropDownClick: (Int) -> Unit,
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.notes_email_label, email),
                        fontSize = FontSizes.large,
                    )
                    Text(
                        text = stringResource(R.string.theme_selector_label),
                        fontSize = FontSizes.small,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box {
                    IconButton(onClick = { onExpandedChange(true) }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { onExpandedChange(false) }
                    ) {
                        AppTheme.entries.forEachIndexed { idx, theme ->
                            DropdownMenuItem(
                                text = { Text(text = theme.displayName) },
                                onClick = {
                                    onButtonDropDownClick(idx)
                                    onExpandedChange(false)
                                }
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Button(
                onClick = onButtonClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.notes_add_button))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.small)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.notes_list_title),
                        fontSize = FontSizes.huge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                itemsIndexed(notes) { index, note ->
                    Text(
                        text = note.title,
                        fontSize = FontSizes.normal,
                        modifier = Modifier
                            .background(
                                if (index % 2 == 0) MaterialTheme.colorScheme.surfaceContainerHighest
                                else MaterialTheme.colorScheme.surface
                            )
                            .padding(Dimens.medium)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

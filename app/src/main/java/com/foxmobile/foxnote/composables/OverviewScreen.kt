package com.foxmobile.foxnote.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foxmobile.foxnote.database.Note
import com.foxmobile.foxnote.database.NoteViewModel
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onNoteClick: (Note) -> Unit, onAddButtonClick: () -> Unit, noteViewModel: NoteViewModel
) {
    val uiState by noteViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "FoxNote Mini",
                        fontSize = 35.sp,
                    )

                },
                modifier = Modifier.wrapContentHeight(),
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddButtonClick()
                }, modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add note",
                    Modifier.size(55.dp)
                )
            }
        }

    ) { padding ->

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.notes.forEach { note ->
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .clickable {
                                onNoteClick(note)
                            }) {
                        NoteContainer(
                            note = note, noteViewModel = getViewModel<NoteViewModel>()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOverviewScreen(modifier: Modifier = Modifier) {
    FoxNoteTheme {
        OverviewScreen(onNoteClick = {}, onAddButtonClick = {}, noteViewModel = getViewModel())
    }
}
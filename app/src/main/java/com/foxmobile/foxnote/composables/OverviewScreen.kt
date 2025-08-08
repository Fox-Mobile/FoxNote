package com.foxmobile.foxnote.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foxmobile.foxnote.database.note.Note
import com.foxmobile.foxnote.database.note.NoteEvent
import com.foxmobile.foxnote.database.note.NoteViewModel
import com.foxmobile.foxnote.database.tag.TagViewModel
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import com.foxmobile.foxnote.ui.theme.brandColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onNoteClick: (Note) -> Unit, onAddButtonClick: () -> Unit, noteViewModel: NoteViewModel
) {
    val uiState by noteViewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openTagDialog = remember { mutableStateOf(false) }

    when {
        openTagDialog.value -> {
            TagDialog(
                modifier = Modifier,
                onDismissRequest = {
                    openTagDialog.value = false
                },
                onTagClicked = {
                },
                tagViewModel = getViewModel(),
            )
        }
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            NavigationDrawerContent(
                onTagsClicked = {
                    scope.launch {
                        drawerState.close()
                    }
                    openTagDialog.value = true
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "FoxNote",
                            fontSize = 35.sp,
                            color = brandColor
                        )

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Open Drawer", tint = brandColor)
                        }
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
                                note = note,
                                noteViewModel = getViewModel<NoteViewModel>(),
                                tagViewModel = getViewModel<TagViewModel>()
                            )
                        }
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
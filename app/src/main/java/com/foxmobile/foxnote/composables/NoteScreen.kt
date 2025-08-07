package com.foxmobile.foxnote.composables

import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foxmobile.foxnote.database.note.Note
import com.foxmobile.foxnote.database.note.NoteEvent
import com.foxmobile.foxnote.database.note.NoteViewModel
import com.foxmobile.foxnote.database.tag.TagViewModel
import com.foxmobile.foxnote.ui.theme.Black
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
    tagViewModel: TagViewModel
) {
    val onBackArrowClick = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val uiState by noteViewModel.state.collectAsState()

    val tagsState by tagViewModel.state.collectAsState()

    val context = LocalContext.current
    var title by remember(uiState.id) { mutableStateOf(uiState.title) }
    var content by remember(uiState.id) { mutableStateOf(uiState.content) }
    var isNoteSaved by  remember { mutableStateOf(false) }
    var isPinned by remember { mutableStateOf(uiState.isPinned) }

    val openTagDialog = remember { mutableStateOf(false) }

    when {
        openTagDialog.value -> {
            TagDialog(
                modifier = Modifier,
                onDismissRequest = {
                    openTagDialog.value = false
                },
                onTagClicked = { tagID ->
                    noteViewModel.onEvent(NoteEvent.SetTagID(tagID))
                    openTagDialog.value = false

                    Toast.makeText(
                        context,
                        "Please save note after adding tag",
                        Toast.LENGTH_LONG
                    ).show()
                },
                tagViewModel = getViewModel(),
            )
        }
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("FoxNote", fontSize = 35.sp)

                    }
                )
            },
        ) { padding ->
            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .fillMaxHeight(),

                verticalArrangement = Arrangement.Top,
            ) {
                when {
                    uiState.tagID != null -> {
                        val tagId = uiState.tagID
                        Card(
                            modifier = modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            shape = RoundedCornerShape(corner = CornerSize(30.dp)),
                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                        ) {
                            Row(
                                modifier.padding(8.dp),
                                Arrangement.Absolute.Right,
                                Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.Label,
                                    contentDescription = "Tag",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = modifier.size(40.dp)
                                )
                                Text(
                                    text = tagId?.let { tagsState.tags.find { it.id == tagId }?.name }
                                        ?: "",
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic
                                )
                                IconButton(
                                    modifier = modifier
                                        .width(48.dp)
                                        .height(48.dp),
                                    onClick = {
                                        noteViewModel.onEvent(NoteEvent.SetTagID(null))
                                        Toast.makeText(
                                            context,
                                            "Please save note after removing tag",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = "Remove tag",
                                        modifier = modifier.size(36.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                TextField(
                    readOnly = isNoteSaved,
                    value = title,
                    onValueChange = { newTitle ->
                        title = newTitle
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),

                    textStyle = TextStyle(
                        fontSize = 28.sp
                    ),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Black,
                        unfocusedContainerColor = Black,
                        disabledContainerColor = Black,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Hint",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Title",
                                fontSize = 28.sp,
                                color = Color.Gray,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                )
                Spacer(modifier = modifier.padding(2.dp))
                TextField(
                    readOnly = isNoteSaved,
                    value = content,
                    onValueChange = { newContent ->
                        content = newContent
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),

                    textStyle = TextStyle(
                        fontSize = 22.sp
                    ),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Black,
                        unfocusedContainerColor = Black,
                        disabledContainerColor = Black,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Hint",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Content",
                                fontSize = 22.sp,
                                color = Color.Gray,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                )
            }
        }

        HorizontalFloatingToolbar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            expanded = true,
            floatingActionButton = {
                FloatingActionButton (
                    onClick = {
                        noteViewModel.onEvent(NoteEvent.SetID(uiState.id))
                        noteViewModel.onEvent(NoteEvent.SetTitle(title))
                        noteViewModel.onEvent(NoteEvent.SetContent(content))
                        if (content.isNotEmpty() || title.isNotEmpty()) {
                            noteViewModel.onEvent(NoteEvent.SaveNote)
                            isNoteSaved = true
                            onBackArrowClick?.onBackPressed()
                        } else Toast.makeText(
                            context,
                            "Can't save note - both fields are empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Save note",
                        tint = Color.White,
                        modifier = modifier.size(48.dp)
                    )
                }
            }
        ) {
            IconButton(
                onClick = {
                    onBackArrowClick?.onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    modifier = modifier.size(48.dp),
                    tint = Color.White
                )
            }

            IconButton(
                onClick = {
                    if(title.isNotEmpty() || content.isNotEmpty()) {
                        if (isPinned) {
                            noteViewModel.onEvent(NoteEvent.SetIsPinned(false))
                            isPinned = false
                            Toast.makeText(
                                context,
                                "Please save note after unpinning it",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            noteViewModel.onEvent(NoteEvent.SetIsPinned(true))
                            isPinned = true
                            Toast.makeText(
                                context,
                                "Please save note after pinning it",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }else Toast.makeText(context, "Can't pin/unpin empty note", Toast.LENGTH_LONG).show()
                }
            ) {
                Icon(
                    imageVector = if (isPinned) {
                        Icons.Filled.PushPin
                    } else Icons.Outlined.PushPin,
                    contentDescription = if (isPinned) {
                        "Unpin note"
                    } else "Pin note",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = {
                    if (uiState.tagID == null) {
                        openTagDialog.value = true
                    }else {
                        Toast.makeText(
                            context,
                            "You can add only one tag to note.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Label,
                    tint = Color.White,
                    contentDescription = "Add tag",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNoteScreen(modifier: Modifier = Modifier) {
    FoxNoteTheme {
        NoteScreen(noteViewModel = getViewModel<NoteViewModel>(),  tagViewModel = getViewModel<TagViewModel>())
    }
}
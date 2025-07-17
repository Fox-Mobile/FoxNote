package com.foxmobile.foxnote.composables

import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.runtime.Composable
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
import com.foxmobile.foxnote.database.Note
import com.foxmobile.foxnote.database.NoteEvent
import com.foxmobile.foxnote.database.NoteViewModel
import com.foxmobile.foxnote.ui.theme.Black
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import com.foxmobile.foxnote.ui.theme.Orange
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    noteParam: Note?,
    noteViewModel: NoteViewModel
) {
    val onBackArrowClick = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val note = noteParam ?: Note(null, "", "", "", "")

    val context = LocalContext.current
    var title by remember(note.id) { mutableStateOf(note.title) }
    var content by remember(note.id) { mutableStateOf(note.content) }
    var isNoteSaved by  remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("FoxNote Mini", fontSize = 35.sp)

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackArrowClick?.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back",
                                modifier = modifier.size(39.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                noteViewModel.onEvent(NoteEvent.SetID(note.id))
                                noteViewModel.onEvent(NoteEvent.SetTitle(title))
                                noteViewModel.onEvent(NoteEvent.SetContent(content))
                                if (content.isNotEmpty() || title.isNotEmpty()) {
                                    noteViewModel.onEvent(NoteEvent.SaveNote)
                                    isNoteSaved = true
                                    Toast.makeText(
                                        context,
                                        "Saving... To edit: view list first.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else Toast.makeText(
                                    context,
                                    "Can't save note - both fields are empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "Save note",
                                tint = Orange,
                                modifier = modifier.size(39.dp)
                            )
                        }
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
                                imageVector = Icons.Default.Info,
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
                                imageVector = Icons.Default.Info,
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

        VerticalFloatingToolbar(
            expanded = true
        ) { }
    }
}

@Preview
@Composable
fun PreviewNoteScreen(modifier: Modifier = Modifier) {
    FoxNoteTheme {
        NoteScreen(noteParam = Note(1, "Title", "Note content", LocalDate.now().toString(), LocalDateTime.now().toString()), noteViewModel = getViewModel<NoteViewModel>())
    }
}
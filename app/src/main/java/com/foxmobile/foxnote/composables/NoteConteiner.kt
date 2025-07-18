package com.foxmobile.foxnote.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foxmobile.foxnote.R
import com.foxmobile.foxnote.database.Note
import com.foxmobile.foxnote.database.NoteEvent
import com.foxmobile.foxnote.database.NoteViewModel
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun NoteContainer(
    modifier: Modifier = Modifier,
    note: Note,
    noteViewModel: NoteViewModel
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row {
                IconButton(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically)
                        .background(MaterialTheme.colorScheme.background),

                    onClick = {
                        if (note.isPinned) {
                            noteViewModel.onEvent(NoteEvent.SetIsPinned(false))
                        }else noteViewModel.onEvent(NoteEvent.SetIsPinned(true))
                        noteViewModel.onEvent(NoteEvent.SetID(note.id))
                        noteViewModel.onEvent(NoteEvent.SetContent(note.content))
                        noteViewModel.onEvent(NoteEvent.SetTitle(note.title))
                        noteViewModel.onEvent(NoteEvent.SaveNote)
                    }
                ) {
                    Icon(
                        imageVector = if (note.isPinned) {
                            ImageVector.vectorResource(R.drawable.round_push_pin_24)
                        } else ImageVector.vectorResource(R.drawable.outline_push_pin_24),
                        contentDescription = if (note.isPinned) {
                            "Unpin note"
                        } else "Pin note",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Text(
                            note.title.ifEmpty { "Untitled" },
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            note.date,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    })
                IconButton(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically)
                        .background(MaterialTheme.colorScheme.background),

                    onClick = {
                        noteViewModel.onEvent(NoteEvent.DeleteNote(note))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNoteContainer(modifier: Modifier = Modifier) {
    FoxNoteTheme {
        NoteContainer(
            modifier = modifier,
            note = Note(
                1,
                "Note",
                "Content",
                LocalDate.now().toString(),
                LocalDateTime.now().toString()
            ),
            noteViewModel = getViewModel()
        )
    }
}
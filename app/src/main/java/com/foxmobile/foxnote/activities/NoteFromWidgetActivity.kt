package com.foxmobile.foxnote.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.foxmobile.foxnote.composables.NoteScreen
import com.foxmobile.foxnote.database.note.Note
import com.foxmobile.foxnote.database.note.NoteEvent
import com.foxmobile.foxnote.database.note.NoteViewModel
import com.foxmobile.foxnote.database.tag.TagViewModel
import com.foxmobile.foxnote.navigation.NavigationRoot
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.foxmobile.foxnote.widgets.NotesWidget
import com.foxmobile.foxnote.widgets.NotesWidget.Companion.EXTRA_NOTE_FROM_WIDGET
import org.koin.androidx.viewmodel.ext.android.getViewModel

class NoteFromWidgetActivity() : ComponentActivity(), KoinComponent {
    private val noteViewModel: NoteViewModel by lazy{ getViewModel() }
    val tagViewModel: TagViewModel by lazy { getViewModel() }
    var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        note = intent.getParcelableExtra(EXTRA_NOTE_FROM_WIDGET, Note::class.java)
        setContent {
            FoxNoteTheme {
                noteViewModel.onEvent(NoteEvent.SetTitle(note!!.title))
                noteViewModel.onEvent(NoteEvent.SetContent(note!!.content))
                noteViewModel.onEvent(NoteEvent.SetID(note!!.id))
                noteViewModel.onEvent(NoteEvent.SetTagID(note!!.tagId))
                noteViewModel.onEvent(NoteEvent.SetIsPinned(note!!.isPinned))

                NoteScreen(tagViewModel = tagViewModel, noteViewModel = noteViewModel)
            }
        }
    }
}

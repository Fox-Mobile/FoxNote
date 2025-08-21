package com.foxmobile.foxnote.database.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.collections.emptyList

class NoteViewModel(
    private val noteDao: NoteDao
) : ViewModel() {
    private val _state = MutableStateFlow(NoteState())
    private val _notes = noteDao.getAllNotes()

    val state = combine(_state, _notes) { state, notes ->
        state.copy(
            notes = notes
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    suspend fun getNotesForWidget(): List<Note> {
        return try {
            noteDao.getAllNotes().first()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteDao.deleteNote(event.note)
                }
            }

            NoteEvent.SaveNote -> {
                val id = _state.value.id
                val title = _state.value.title
                val content = _state.value.content
                val date = LocalDate.now().toString()
                val dateTime = LocalDateTime.now().toString()
                val isPinned = _state.value.isPinned
                val tagId = _state.value.tagID

                val note = Note(
                    id = id, title = title, content = content, date = date, dateTime = dateTime, isPinned = isPinned, tagId = tagId
                )

                viewModelScope.launch {
                    noteDao.upsertNote(note)
                }

                _state.update {
                    it.copy(
                        id = null, title = "", content = "", date = "", dateTime = "", isPinned = false
                    )
                }
            }

            is NoteEvent.SetContent -> {
                _state.value = _state.value.copy(
                    content = event.content
                )
            }

            is NoteEvent.SetID -> {
                _state.update {
                    it.copy(
                        id = event.id
                    )
                }
            }

            is NoteEvent.SetTitle -> {
                _state.value = _state.value.copy(
                    title = event.title
                )
            }

            is NoteEvent.SetIsPinned -> {
                _state.value = _state.value.copy(
                    isPinned = event.isPinned
                )
            }

            is NoteEvent.SetTagID -> {
                _state.value = _state.value.copy(
                    tagID = event.tagID
                )
            }
        }
    }

}
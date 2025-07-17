package com.foxmobile.foxnote.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class NoteViewModel(
    private val noteDao: NoteDao
) : ViewModel() {
    private val _state = MutableStateFlow(NoteState())
    private val _notes = noteDao.getAll()

    val state = combine(_state, _notes) { state, notes ->
        state.copy(
            notes = notes
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

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

                val note = Note(
                    id = id, title = title, content = content, date = date, dateTime = dateTime
                )

                viewModelScope.launch {
                    noteDao.upsertNote(note)
                }

                _state.update {
                    it.copy(
                        id = null, title = "", content = "", date = "", dateTime = ""
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
        }
    }

}
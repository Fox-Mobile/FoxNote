package com.foxmobile.foxnote.database.note

sealed interface NoteEvent {
    data object SaveNote: NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
    data class SetTitle(val title: String): NoteEvent
    data class SetContent(val content: String): NoteEvent
    data class SetID(val id: Int?): NoteEvent
    data class SetIsPinned(val isPinned: Boolean): NoteEvent
    data class SetTagID(val tagID: Int?): NoteEvent
}
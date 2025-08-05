package com.foxmobile.foxnote.database.note

data class NoteState(
    val id : Int? = null,
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val dateTime: String = "",
    val isPinned: Boolean = false,
    val tagID: Int? = null,
    val notes: List<Note> = emptyList()
    )
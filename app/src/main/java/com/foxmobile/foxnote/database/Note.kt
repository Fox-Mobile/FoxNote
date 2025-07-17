package com.foxmobile.foxnote.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val date: String,
    val dateTime: String,
    val isPinned: Boolean = false
)

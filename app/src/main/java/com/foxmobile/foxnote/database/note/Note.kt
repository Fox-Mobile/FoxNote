package com.foxmobile.foxnote.database.note

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val date: String,
    val dateTime: String,
    val isPinned: Boolean = false,
    val tagId: Int? = null
): Parcelable

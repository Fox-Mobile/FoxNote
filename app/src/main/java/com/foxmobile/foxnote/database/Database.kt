package com.foxmobile.foxnote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foxmobile.foxnote.database.note.Note
import com.foxmobile.foxnote.database.note.NoteDao
import com.foxmobile.foxnote.database.tag.Tag
import com.foxmobile.foxnote.database.tag.TagDao

@Database(
    entities = [Note::class, Tag::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val tagDao: TagDao
}
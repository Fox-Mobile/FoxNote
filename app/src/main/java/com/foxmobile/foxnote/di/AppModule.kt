package com.foxmobile.foxnote.di

import androidx.room.Room
import com.foxmobile.foxnote.database.NoteDao
import com.foxmobile.foxnote.database.NoteDatabase
import com.foxmobile.foxnote.database.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }
    single<NoteDao> {
        get<NoteDatabase>().dao
    }
    viewModel {
        NoteViewModel(get())
    }
}
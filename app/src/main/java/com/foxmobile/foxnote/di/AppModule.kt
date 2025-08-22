package com.foxmobile.foxnote.di

import androidx.room.Room
import com.foxmobile.foxnote.database.note.NoteDao
import com.foxmobile.foxnote.database.Database
import com.foxmobile.foxnote.database.note.NoteViewModel
import com.foxmobile.foxnote.database.tag.TagDao
import com.foxmobile.foxnote.database.tag.TagViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "notes.db"
        ).build()
    }

    single<NoteDao> {
        get<Database>().noteDao
    }

    single<TagDao> {
        get<Database>().tagDao
    }


    viewModel<NoteViewModel> {
        NoteViewModel(get(), androidApplication())
    }

    viewModel<TagViewModel>{
        TagViewModel(get())
    }
}
package com.antonvinicius.keepnotes.di

import androidx.room.Room
import com.antonvinicius.keepnotes.database.AppDatabase
import com.antonvinicius.keepnotes.database.dao.NoteDao
import com.antonvinicius.keepnotes.repository.NoteRepository
import com.antonvinicius.keepnotes.retrofit.RetrofitInstance
import com.antonvinicius.keepnotes.retrofit.WebClient
import com.antonvinicius.keepnotes.ui.viewmodel.NoteCreateOrEditViewModel
import com.antonvinicius.keepnotes.ui.viewmodel.NoteDetailViewModel
import com.antonvinicius.keepnotes.ui.viewmodel.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "note_database"
        ).build()
    }

    single<NoteDao> {
        get<AppDatabase>().noteDao()
    }

    single<RetrofitInstance> {
        RetrofitInstance()
    }

    single<WebClient> {
        WebClient(get())
    }

    single<NoteRepository> {
        NoteRepository(get(), get())
    }

    viewModel { NotesViewModel(get()) }
    viewModel { (id: String) -> NoteDetailViewModel(get(), id) }
    viewModel { (id: String?) -> NoteCreateOrEditViewModel(get(), id) }
}
package com.antonvinicius.keepnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.antonvinicius.keepnotes.database.dao.NoteDao
import com.antonvinicius.keepnotes.model.NoteDto

@Database(entities = [NoteDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
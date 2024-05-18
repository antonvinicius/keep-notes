package com.antonvinicius.keepnotes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.antonvinicius.keepnotes.model.NoteDto

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun notesGetAll(): LiveData<List<NoteDto>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun noteById(id: String): LiveData<NoteDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun noteSaveAll(notes: List<NoteDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun noteSave(note: NoteDto)

    @Update
    suspend fun noteUpdate(note: NoteDto)

    @Delete
    suspend fun noteDelete(note: NoteDto)
}
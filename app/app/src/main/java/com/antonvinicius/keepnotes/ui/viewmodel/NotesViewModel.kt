package com.antonvinicius.keepnotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.repository.NoteRepository
import com.antonvinicius.keepnotes.retrofit.ApiResult

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    val apiResultLiveData = repository.apiResultLiveData

    suspend fun fetchNotes() {
        repository.fetchNotes()
    }
}
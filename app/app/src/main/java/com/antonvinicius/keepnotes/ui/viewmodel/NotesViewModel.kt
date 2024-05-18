package com.antonvinicius.keepnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.antonvinicius.keepnotes.repository.NoteRepository

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    val resultLiveData = repository.mediator

    suspend fun fetchNotes() {
        repository.fetchNotes()
    }
}
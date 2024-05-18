package com.antonvinicius.keepnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonvinicius.keepnotes.repository.NoteRepository

class NoteCreateOrEditViewModelFactory(
    private val noteRepository: NoteRepository, private val noteId: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteCreateOrEditViewModel(noteRepository, noteId) as T
    }
}
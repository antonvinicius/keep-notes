package com.antonvinicius.keepnotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.repository.NoteRepository
import com.antonvinicius.keepnotes.retrofit.ApiResult

class NoteDetailViewModel(
    private val noteRepository: NoteRepository, private val noteId: String?
) : ViewModel() {
    val noteLiveData = noteRepository.noteLiveData

    suspend fun noteRemove(): LiveData<ApiResult<NoteDto>>? {
        noteId?.let { return noteRepository.noteDelete(it) }
        return null
    }

    suspend fun noteFindById() {
        noteId?.let { noteRepository.noteById(it) }
    }
}
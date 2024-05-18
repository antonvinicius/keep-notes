package com.antonvinicius.keepnotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.antonvinicius.keepnotes.model.NoteCreateDto
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.repository.NoteRepository
import com.antonvinicius.keepnotes.retrofit.ApiResult

class NoteCreateOrEditViewModel(
    private val noteRepository: NoteRepository, private val noteId: String?
) : ViewModel() {
    fun findNote() = noteId?.let { noteRepository.noteById(it) }

    fun handleCreateOrUpdateEvents(
        whenCreate: () -> Unit, whenUpdate: () -> Unit
    ) {
        if (noteId == null) whenCreate() else whenUpdate()
    }

    suspend fun save(title: String, content: String): LiveData<ApiResult<NoteDto>> {
        val isCreate = noteId == null

        if (isCreate) {
            val noteCreateDto = NoteCreateDto(content = content, title = title)
            return noteRepository.noteCreate(noteCreateDto)
        } else {
            val noteDto = NoteDto(
                id = noteId!!, content = content, title = title
            )
            return noteRepository.noteUpdate(noteId, noteDto)
        }
    }
}
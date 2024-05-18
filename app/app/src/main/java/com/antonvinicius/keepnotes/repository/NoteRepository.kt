package com.antonvinicius.keepnotes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.antonvinicius.keepnotes.model.NoteCreateDto
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.retrofit.ApiResult
import com.antonvinicius.keepnotes.retrofit.WebClient
import com.antonvinicius.keepnotes.util.AppException
import kotlinx.coroutines.delay

class NoteRepository(
    private val webClient: WebClient
) {
    private var _notes = MutableLiveData<ApiResult<List<NoteDto>?>>()
    val apiResultLiveData: LiveData<ApiResult<List<NoteDto>?>> get() = _notes

    suspend fun fetchNotes() {
        try {
            _notes.value = ApiResult.Loading()
            delay(3000)
            val response = webClient.notesGet()
            _notes.value = ApiResult.Success(response)
        } catch (error: AppException) {
            _notes.value = ApiResult.Error(_notes.value?.data, error.message)
        }
    }

    private var _note = MutableLiveData<ApiResult<NoteDto?>>()
    val noteLiveData: LiveData<ApiResult<NoteDto?>> get() = _note

    suspend fun noteById(id: String) {
        try {
            _note.value = ApiResult.Loading()
            val response = webClient.noteById(id)
            _note.value = ApiResult.Success(response)
        } catch (error: AppException) {
            _note.value = ApiResult.Error(_note.value?.data, error.message)
        }
    }

    suspend fun noteUpdate(id: String, note: NoteDto) = liveData {
        try {
            emit(ApiResult.Loading())
            val response = webClient.noteUpdate(id, note)
            emit(ApiResult.Success(response))
        } catch (error: AppException) {
            emit(ApiResult.Error(null, error.message))
        }
    }

    suspend fun noteDelete(id: String) = liveData {
        try {
            emit(ApiResult.Loading())
            val response = webClient.noteDelete(id)
            emit(ApiResult.Success(response))
        } catch (error: AppException) {
            emit(ApiResult.Error(null, error.message))
        }
    }

    suspend fun noteCreate(note: NoteCreateDto) = liveData {
        try {
            emit(ApiResult.Loading())
            val response = webClient.noteCreate(note)
            emit(ApiResult.Success(response))
        } catch (error: AppException) {
            emit(ApiResult.Error(null, error.message))
        }
    }
}
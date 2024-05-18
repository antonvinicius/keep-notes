package com.antonvinicius.keepnotes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.antonvinicius.keepnotes.database.dao.NoteDao
import com.antonvinicius.keepnotes.model.NoteCreateDto
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.retrofit.ApiResult
import com.antonvinicius.keepnotes.retrofit.WebClient
import com.antonvinicius.keepnotes.util.AppException
import kotlinx.coroutines.delay

class NoteRepository(
    private val webClient: WebClient,
    private val noteDao: NoteDao
) {
    private var _mediator = MediatorLiveData<ApiResult<List<NoteDto>>>()
    val mediator: LiveData<ApiResult<List<NoteDto>>> get() = _mediator

    suspend fun fetchNotes() {
        val failResponseLiveData = MutableLiveData<ApiResult<List<NoteDto>>>()
        try {
            _mediator.addSource(noteDao.notesGetAll()) {
                if (it.isNotEmpty()) _mediator.value = ApiResult.Success(it)
            }
            _mediator.addSource(failResponseLiveData) { failResponseFromApi ->
                _mediator.value = failResponseFromApi
            }
            val response = webClient.notesGet()
            noteDao.noteSaveAll(response)
        } catch (error: AppException) {
            failResponseLiveData.value = ApiResult.Error(_mediator.value?.data, error.message)
        }
    }

    fun noteById(id: String) = noteDao.noteById(id)

    suspend fun noteUpdate(id: String, note: NoteDto) = liveData {
        try {
            emit(ApiResult.Loading())
            val response = webClient.noteUpdate(id, note)
            noteDao.noteSave(response)
            emit(ApiResult.Success(response))
        } catch (error: AppException) {
            emit(ApiResult.Error(null, error.message))
        }
    }

    suspend fun noteDelete(id: String) = liveData {
        try {
            emit(ApiResult.Loading())
            val response = webClient.noteDelete(id)
            noteDao.noteDelete(response)
            emit(ApiResult.Success(response))
        } catch (error: AppException) {
            emit(ApiResult.Error(null, error.message))
        }
    }

    suspend fun noteCreate(note: NoteCreateDto) = liveData {
        try {
            emit(ApiResult.Loading())
            val response = webClient.noteCreate(note)
            noteDao.noteSave(response)
            emit(ApiResult.Success(response))
        } catch (error: AppException) {
            emit(ApiResult.Error(null, error.message))
        }
    }
}
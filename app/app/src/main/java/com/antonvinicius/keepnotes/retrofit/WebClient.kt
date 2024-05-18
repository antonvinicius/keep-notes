package com.antonvinicius.keepnotes.retrofit

import com.antonvinicius.keepnotes.model.NoteCreateDto
import com.antonvinicius.keepnotes.model.NoteDto
import com.antonvinicius.keepnotes.util.AppException
import kotlinx.coroutines.CancellationException
import retrofit2.Response

class WebClient(
    private val retrofit: RetrofitInstance
) {
    private suspend fun <T> makeRequest(service: suspend () -> Response<T>): T {
        try {
            val response = service()

            if (response.isSuccessful) {
                return response.body()!!
            } else {
                throw AppException("Requisição inválida.")
            }
        } catch (exception: Exception) {
            if (exception is CancellationException) {
                throw exception
            }

            throw AppException("Ocorreu um erro ao realizar a operação. Tente novamente mais tarde")
        }
    }

    suspend fun notesGet(): List<NoteDto> {
        return makeRequest { retrofit.noteService.notesGet() }
    }

    suspend fun noteById(id: String): NoteDto {
        return makeRequest { retrofit.noteService.noteById(id) }
    }

    suspend fun noteUpdate(id: String, noteDto: NoteDto): NoteDto {
        return makeRequest { retrofit.noteService.noteUpdate(id, noteDto) }
    }

    suspend fun noteDelete(id: String): NoteDto {
        return makeRequest { retrofit.noteService.noteDelete(id) }
    }

    suspend fun noteCreate(noteCreateDto: NoteCreateDto): NoteDto {
        return makeRequest { retrofit.noteService.noteCreate(noteCreateDto) }
    }
}
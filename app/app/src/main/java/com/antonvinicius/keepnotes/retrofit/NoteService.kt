package com.antonvinicius.keepnotes.retrofit

import com.antonvinicius.keepnotes.model.NoteCreateDto
import com.antonvinicius.keepnotes.model.NoteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteService {
    @GET("notes")
    suspend fun notesGet(): Response<List<NoteDto>>

    @GET("notes/{id}")
    suspend fun noteById(@Path("id") id: String): Response<NoteDto>

    @PUT("notes/{id}")
    suspend fun noteUpdate(@Path("id") id: String, @Body note: NoteDto): Response<NoteDto>

    @DELETE("notes/{id}")
    suspend fun noteDelete(@Path("id") id: String): Response<NoteDto>

    @POST("notes")
    suspend fun noteCreate(@Body note: NoteCreateDto): Response<NoteDto>
}
package com.antonvinicius.keepnotes.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("http://192.168.100.161:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val noteService: NoteService by lazy {
        retrofit.create(NoteService::class.java)
    }
}
package com.antonvinicius.keepnotes.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    @SerialName("content")
    val content: String = "",
    @SerialName("id")
    val id: String = "",
    @SerialName("title")
    val title: String = ""
)
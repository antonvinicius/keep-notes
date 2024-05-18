package com.antonvinicius.keepnotes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteCreateDto(
    @SerialName("content") val content: String = "", @SerialName("title") val title: String = ""
)
package com.antonvinicius.keepnotes.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "notes")
@Serializable
data class NoteDto(
    @SerialName("content") val content: String = "",
    @SerialName("id") @PrimaryKey(autoGenerate = false) val id: String = "",
    @SerialName("title") val title: String = ""
)
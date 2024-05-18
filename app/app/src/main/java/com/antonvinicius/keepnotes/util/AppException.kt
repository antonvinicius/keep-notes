package com.antonvinicius.keepnotes.util

data class AppException(
    override val message: String
) : Throwable()
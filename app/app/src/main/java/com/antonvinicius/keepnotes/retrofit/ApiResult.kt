package com.antonvinicius.keepnotes.retrofit

sealed class ApiResult<out T>(
    val data: T?, val error: String? = null
) {
    class Success<out R>(data: R) : ApiResult<R>(data)
    class Error<out R>(data: R?, error: String) : ApiResult<R>(data, error)
    class Loading : ApiResult<Nothing>(null)
}


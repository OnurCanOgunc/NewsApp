package com.decode.composenews.util

sealed class NewsResult<out T> {
    data class Success<out T>(val data: T?) : NewsResult<T>()
    data class Error(val exception: String) : NewsResult<Nothing>()
}

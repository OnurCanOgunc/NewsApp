package com.decode.composenews.data.remote.dto

data class NewsDto(
    val news: List<NewsResponse> = listOf(),
    val page: Int = 0,
    val status: String = ""
)
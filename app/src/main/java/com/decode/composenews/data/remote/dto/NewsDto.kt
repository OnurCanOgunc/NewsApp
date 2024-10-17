package com.decode.composenews.data.remote.dto

data class NewsDto(
    val news: List<News> = listOf(),
    val page: Int = 0,
    val status: String = ""
)
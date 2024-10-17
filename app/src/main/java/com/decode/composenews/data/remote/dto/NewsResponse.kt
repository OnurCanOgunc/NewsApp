package com.decode.composenews.data.remote.dto

data class NewsResponse(
    val author: String = "",
    val category: List<String> = listOf(),
    val description: String = "",
    val id: String = "",
    val image: String = "",
    val language: String = "",
    val published: String = "",
    val title: String = "",
    val url: String = ""
)
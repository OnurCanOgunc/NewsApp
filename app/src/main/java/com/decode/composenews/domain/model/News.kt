package com.decode.composenews.domain.model

data class News(
    val id: String ,
    val author: String = "",
    val category: List<String> = listOf(),
    val description: String = "",
    val image: String = "",
    val published: String = "",
    val title: String = "",
    val url: String = ""
)

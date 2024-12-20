package com.decode.composenews.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class News(
    val id: String ,
    val author: String = "",
    val category: List<String> = listOf(),
    val description: String = "",
    val image: String = "",
    val published: String = "",
    val title: String = "",
    val url: String = "",
    val saved: Boolean = false
)

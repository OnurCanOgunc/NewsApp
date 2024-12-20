package com.decode.composenews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
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

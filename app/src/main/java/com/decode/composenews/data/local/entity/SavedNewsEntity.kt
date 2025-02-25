package com.decode.composenews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news")
data class SavedNewsEntity(
    @PrimaryKey
    val id: String ,
    val author: String = "",
    val category: List<String> = listOf(),
    val description: String = "",
    val image: String = "",
    val published: String = "",
    val title: String = "",
    val url: String = "",
)

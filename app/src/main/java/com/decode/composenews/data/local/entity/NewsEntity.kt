package com.decode.composenews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int ,
    val author: String = "",
    val category: List<String> = listOf(),
    val description: String = "",
    val image: String = "",
    val published: String = "",
    val title: String = "",
    val url: String = ""
)

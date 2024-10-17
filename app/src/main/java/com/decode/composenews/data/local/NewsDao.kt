package com.decode.composenews.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.decode.composenews.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Upsert
    suspend fun upsertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun getAllNews(): PagingSource<Int,NewsEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()
}
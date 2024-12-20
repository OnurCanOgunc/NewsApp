package com.decode.composenews.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decode.composenews.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun getAllNews(): PagingSource<Int,NewsEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getArticle(id: String): NewsEntity?

    @Query("UPDATE news SET saved = :saved WHERE id = :id")
    suspend fun updateSavedStatus(id: String, saved: Boolean)

    @Query("SELECT * FROM news WHERE saved = 1")
    fun getSavedNews(): Flow<List<NewsEntity>>
}

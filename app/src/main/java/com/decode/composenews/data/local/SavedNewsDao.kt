package com.decode.composenews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decode.composenews.data.local.entity.SavedNewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedNews(savedNews: SavedNewsEntity)

    @Delete
    suspend fun deleteSavedNews(news: SavedNewsEntity)

    @Query("SELECT * FROM saved_news")
    fun getAllSavedNews(): Flow<List<SavedNewsEntity>>

    @Query("SELECT id FROM saved_news")
    suspend fun getAllSavedNewsId(): List<String>
}
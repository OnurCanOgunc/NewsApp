package com.decode.composenews.domain.repository

import androidx.paging.PagingData
import com.decode.composenews.domain.model.News
import com.decode.composenews.util.NewsResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(category: String): Flow<PagingData<News>>
    fun getSearchNews(keyword: String, category: String): Flow<PagingData<News>>
    fun getArticle(newsId: String): Flow<NewsResult<News>>
    suspend fun saveArticle(news: News)
    suspend fun deleteArticle(news: News)
    fun getSavedNews(): Flow<List<News>>
}
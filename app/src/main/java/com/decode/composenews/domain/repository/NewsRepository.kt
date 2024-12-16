package com.decode.composenews.domain.repository

import androidx.paging.PagingData
import com.decode.composenews.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(category: String?): Flow<PagingData<News>>
    fun getSearchNews(keyword: String, category: String?): Flow<PagingData<News>>
}
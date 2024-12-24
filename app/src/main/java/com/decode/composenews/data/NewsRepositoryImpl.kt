package com.decode.composenews.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.decode.composenews.data.local.NewsDatabase
import com.decode.composenews.data.mapper.toNews
import com.decode.composenews.data.mapper.toSavedNewsEntity
import com.decode.composenews.data.remote.NewsService
import com.decode.composenews.data.paging.NewsRemoteMediator
import com.decode.composenews.data.paging.SearchNewsPagingSource
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.repository.NewsRepository
import com.decode.composenews.util.Constants.INITIAL_LOAD_SIZE
import com.decode.composenews.util.Constants.PAGE_SIZE
import com.decode.composenews.util.Constants.PREFETCH_DISTANCE
import com.decode.composenews.util.NewsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDb: NewsDatabase,
    private val newsService: NewsService
) : NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNews(category: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            remoteMediator = NewsRemoteMediator(
                newsDatabase = newsDb,
                newsService = newsService,
                category = category
            ),
            pagingSourceFactory = {
                newsDb.newsDao().getAllNews()
            }
        ).flow.map { pagingData->
            pagingData.map { newsEntity ->
                newsEntity.toNews()
            }
        }.catch { e ->
            emit(PagingData.empty())
            Log.e("NewsRepositoryImpl", "Error: ${e.message}")

        }

    }

    override fun getSearchNews(keyword: String,category: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsService = newsService,
                    keyword = keyword,
                    category = category
                )
            }
        ).flow.catch {
            emit(PagingData.empty())
            Log.e("NewsRepositoryImpl", "Error: ${it.message}")
        }
    }

    override fun getArticle(newsId: String): Flow<NewsResult<News>> {
        return flow {
            val result = newsDb.newsDao().getArticle(newsId)?.toNews()
                result?.let {
                    emit(NewsResult.Success(it))
                }?: emit(NewsResult.Error("Article not found"))

        }.catch { exception ->
            Log.e("NewsRepositoryImpl", "Error: ${exception.message}")
            emit(NewsResult.Error(exception.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveArticle(news: News) {
        val savedNews = news.toSavedNewsEntity()
        newsDb.savedNewsDao().insertSavedNews(savedNews)
    }

    override fun getSavedNews(): Flow<List<News>> {
        return newsDb.savedNewsDao().getAllSavedNews().map {
            it.map { savedNewsEntity ->
                savedNewsEntity.toNews()
            }
        }.flowOn(Dispatchers.IO)
    }
}
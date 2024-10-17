package com.decode.composenews.data.remote.mediator

import retrofit2.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.decode.composenews.data.local.NewsDatabase
import com.decode.composenews.data.local.entity.NewsEntity
import com.decode.composenews.data.remote.NewsService
import com.decode.composenews.data.mapper.toNewsEntity
import com.decode.composenews.util.Constants.PAGE
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsService: NewsService,
    private val newsDatabase: NewsDatabase,
    private val language: String
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let {
                        (it.id / state.config.pageSize) + 1
                    } ?: PAGE

                }
            }
            val news = newsService.getNews(
                language = language,
                pageSize = state.config.pageSize,
                page = loadKey
            )
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) newsDatabase.newsDao().deleteAllNews()
                val newsEntities = news.news.map { it.toNewsEntity() }
                newsDatabase.newsDao().upsertAll(newsEntities)
            }

            MediatorResult.Success(endOfPaginationReached = news.news.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
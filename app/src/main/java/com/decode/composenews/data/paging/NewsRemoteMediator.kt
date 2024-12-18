package com.decode.composenews.data.paging

import android.util.Log
import retrofit2.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.decode.composenews.data.local.NewsDatabase
import com.decode.composenews.data.local.entity.NewsEntity
import com.decode.composenews.data.local.entity.RemoteKeyEntity
import com.decode.composenews.data.remote.NewsService
import com.decode.composenews.data.mapper.toNewsEntity
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsService: NewsService,
    private val newsDatabase: NewsDatabase,
    private val category: String? = null
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = state.anchorPosition?.let { position ->
                        state.closestItemToPosition(position)?.id?.let { id ->
                            newsDatabase.remoteKeyDao().getById(id)
                        }
                    }
                    remoteKey?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { news ->
                        newsDatabase.remoteKeyDao().getById(news.id)
                    }
                    val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextKey
                }
            }
            val news = newsService.getNews(
                pageSize = state.config.pageSize,
                page = loadKey,
                category = category
            )
            val nextKey = if (news.news.isEmpty()) null else loadKey + 1
            val prevKey = if (loadKey == 1) null else loadKey - 1

            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    newsDatabase.newsDao().deleteAllNews()
                    newsDatabase.remoteKeyDao().clearRemoteKeys()
                }
                val keys = news.news.map {
                    RemoteKeyEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                newsDatabase.remoteKeyDao().insertAll(keys)
                val newsEntities = news.news.map { it.toNewsEntity() }
                newsDatabase.newsDao().insertAll(newsEntities)
            }

            MediatorResult.Success(endOfPaginationReached = news.news.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}
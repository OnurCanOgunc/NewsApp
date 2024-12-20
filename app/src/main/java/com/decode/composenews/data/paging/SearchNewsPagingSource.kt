package com.decode.composenews.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.decode.composenews.data.mapper.toNews
import com.decode.composenews.data.remote.NewsService
import com.decode.composenews.domain.model.News
import okio.IOException
import retrofit2.HttpException

class SearchNewsPagingSource(
    private val newsService: NewsService,
    private val keyword: String,
    val category: String
): PagingSource<Int, News>() {

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val currentPage = params.key ?: FIRST_PAGE_INDEX
        return try {
            val news = newsService.getNSearchNews(
                keyword = keyword,
                pageSize = params.loadSize,
                page = currentPage,
                category = category
            )
            Log.e("SearchNewsPagingSource", "news: $news")

            val endOfPaginationReached = news.news.isEmpty()

            LoadResult.Page(
                data = news.news.map { it.toNews() },
                prevKey = if (currentPage == FIRST_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

}

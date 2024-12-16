package com.decode.composenews.data.remote

import com.decode.composenews.data.remote.dto.NewsDto
import com.decode.composenews.util.Constants.END_POINT_LATEST_NEWS
import com.decode.composenews.util.Constants.END_POINT_SEARCH
import com.decode.composenews.util.Constants.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET(END_POINT_LATEST_NEWS)
    suspend fun getNews(
        @Query("category") category: String? = "sports",
        @Query("language") language: String = LANGUAGE,
        @Query("page_size") pageSize: Int,
        @Query("page_number") page: Int): NewsDto

    @GET(END_POINT_SEARCH)
    suspend fun getNSearchNews(
        @Query("categories") category: String?,
        @Query("keywords") keyword: String,
        @Query("language") language: String = LANGUAGE,
        @Query("page_size") pageSize: Int,
        @Query("page_number") page: Int): NewsDto


}
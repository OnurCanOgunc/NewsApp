package com.decode.composenews.data.remote

import com.decode.composenews.data.remote.dto.NewsDto
import com.decode.composenews.util.Constants.END_POINT
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET(END_POINT)
    suspend fun getNews(
        @Query("language") language: String,
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int): NewsDto
}
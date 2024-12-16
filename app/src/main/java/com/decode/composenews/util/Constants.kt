package com.decode.composenews.util

import com.decode.composenews.BuildConfig

object Constants {
    const val BASE_URL = "https://api.currentsapi.services/v1/"
    const val END_POINT_LATEST_NEWS = "latest-news"
    const val END_POINT_SEARCH = "search"
    const val LANGUAGE = "en"
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 30
    const val PREFETCH_DISTANCE = 5
    const val API_KEY = BuildConfig.API_KEY
}



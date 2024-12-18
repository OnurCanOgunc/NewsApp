package com.decode.composenews.domain.usecase

import androidx.paging.PagingData
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(keyword: String, category: String): Flow<PagingData<News>> =
        newsRepository.getSearchNews(keyword = keyword, category = category).flowOn(Dispatchers.IO)
}
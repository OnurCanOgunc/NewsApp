package com.decode.composenews.domain.usecase

import com.decode.composenews.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(keyword: String) =
        newsRepository.getSearchNews(keyword).flowOn(Dispatchers.IO)
}
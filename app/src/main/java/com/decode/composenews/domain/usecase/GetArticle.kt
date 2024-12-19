package com.decode.composenews.domain.usecase

import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.repository.NewsRepository
import com.decode.composenews.util.NewsResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticle @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(newsId: String): Flow<NewsResult<News>> = newsRepository.getArticle(newsId)
}
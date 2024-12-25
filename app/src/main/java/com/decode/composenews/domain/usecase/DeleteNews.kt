package com.decode.composenews.domain.usecase

import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteNews @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(news: News) = newsRepository.deleteArticle(news)
}
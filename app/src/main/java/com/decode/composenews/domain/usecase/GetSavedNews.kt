package com.decode.composenews.domain.usecase

import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedNews @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(): Flow<List<News>> = newsRepository.getSavedNews()
}
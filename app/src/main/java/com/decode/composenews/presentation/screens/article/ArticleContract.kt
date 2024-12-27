package com.decode.composenews.presentation.screens.article

import com.decode.composenews.domain.model.News

object ArticleContract {
    data class ArticleUIState(
        val news: News? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )
    sealed class ArticleUIEvent {
        data class Load(val id: String) : ArticleUIEvent()
        data class Save(val news: News?) : ArticleUIEvent()
    }
    sealed class ArticleUIEffect {
        data class SaveMessage(val message: String) : ArticleUIEffect()
    }
}
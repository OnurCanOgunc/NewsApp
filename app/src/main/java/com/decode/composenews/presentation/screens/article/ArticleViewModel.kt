package com.decode.composenews.presentation.screens.article

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.usecase.GetArticle
import com.decode.composenews.domain.usecase.SaveArticle
import com.decode.composenews.presentation.screens.article.ArticleContract.ArticleUIEffect
import com.decode.composenews.presentation.screens.article.ArticleContract.ArticleUIEvent
import com.decode.composenews.presentation.screens.article.ArticleContract.ArticleUIState
import com.decode.composenews.util.NewsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticle: GetArticle,
    private val savedArticle: SaveArticle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleUIState())
    val uiState: StateFlow<ArticleUIState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ArticleUIEffect>()
    val uiEffect: SharedFlow<ArticleUIEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: ArticleUIEvent) {
        when (event) {
            is ArticleUIEvent.Load -> loadArticle(event.id)
            is ArticleUIEvent.Save -> saveArticle(event.news)
        }
    }

    fun loadArticle(id: String) {
        if (_uiState.value.news != null) {
            _uiState.value = _uiState.value.copy(isError = true)
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            getArticle(id).collect { result ->
                when (result) {
                    is NewsResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            news = result.data,
                            isLoading = false,
                            isError = false
                        )
                    }

                    is NewsResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    private fun saveArticle(news: News?) {
        viewModelScope.launch {
            news?.let {
                val updatedNews = news.copy(saved = !news.saved)
                savedArticle(updatedNews)
                Log.d("ArticleViewModel", "Article saved: ${updatedNews.saved}")
                _uiState.value = _uiState.value.copy(
                    news = updatedNews
                )
                if (news.saved) {
                    _uiEffect.emit(ArticleUIEffect.SaveMessage("News removed from saved."))
                } else {
                    _uiEffect.emit(ArticleUIEffect.SaveMessage("News added to saved."))
                }
            }
        }
    }

}
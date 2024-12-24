package com.decode.composenews.presentation.screens.article

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.usecase.GetArticle
import com.decode.composenews.domain.usecase.SaveArticle
import com.decode.composenews.presentation.screens.article.ArticleContract.ArticleUIEvent
import com.decode.composenews.presentation.screens.article.ArticleContract.ArticleUIState
import com.decode.composenews.util.NewsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
                        Log.d("ArticleViewModel", "News loaded: ${result.data}")
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
            if (news != null) {
                savedArticle(news.copy(saved = !news.saved))
                _uiState.value = _uiState.value.copy(news = news.copy(saved = !news.saved))

            }
        }
        // Kaydetme işlemi başarılıysa UIEffect tetiklenebilir:

    }
}
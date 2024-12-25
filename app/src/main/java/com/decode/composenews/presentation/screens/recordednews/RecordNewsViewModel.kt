package com.decode.composenews.presentation.screens.recordednews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.usecase.DeleteNews
import com.decode.composenews.domain.usecase.GetSavedNews
import com.decode.composenews.domain.usecase.SaveArticle
import com.decode.composenews.presentation.screens.recordednews.RecordNewsContract.RecordNewsUIEvent
import com.decode.composenews.presentation.screens.recordednews.RecordNewsContract.RecordNewsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordNewsViewModel @Inject constructor(
    private val getSavedNews: GetSavedNews,
    private val savedArticle: SaveArticle, //--> name
    private val deleteNews: DeleteNews,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordNewsUIState())
    val uiState = _uiState.onStart {
        loadSavedArticles()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RecordNewsUIState()
    )
    fun onEvent(event: RecordNewsUIEvent) {
        when (event) {
            RecordNewsUIEvent.LoadSavedArticles -> loadSavedArticles()
            is RecordNewsUIEvent.SaveArticle -> saveArticle(event.news)
            is RecordNewsUIEvent.DeleteArticle -> deleteArticle(event.news)
        }
    }

    private fun saveArticle(news: News) {
        viewModelScope.launch {
            savedArticle(news.copy(saved = !news.saved))
            if (news.saved) {
                deleteArticle(news)
            } else {
                savedArticle(news)
            }
            _uiState.value = _uiState.value.copy(
                news = _uiState.value.news.map {
                    if (it.id == news.id) {
                        it.copy(saved = !it.saved)
                    } else {
                        it
                    }
                }
            )
        }
    }

    private fun deleteArticle(news: News) {
        viewModelScope.launch {
            deleteNews(news)
            _uiState.value = _uiState.value.copy(
                news = _uiState.value.news.filter { it.id != news.id }
            )
        }
    }

    private fun loadSavedArticles() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getSavedNews().collect { news ->
                    _uiState.value = _uiState.value.copy(news = news, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}


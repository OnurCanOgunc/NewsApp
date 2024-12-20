package com.decode.composenews.presentation.screens.recordednews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.usecase.GetSavedNews
import com.decode.composenews.domain.usecase.SaveArticle
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
    private val savedArticle: SaveArticle //--> name
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordNewsUIState())
    val uiState = _uiState.onStart {
        loadSavedArticles()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RecordNewsUIState()
    )

    fun saveArticle(news: News) {
        viewModelScope.launch {
            savedArticle(news.copy(saved = !news.saved))
        }
    }

    fun loadSavedArticles() {
        viewModelScope.launch {
            getSavedNews().collect {
                _uiState.value = _uiState.value.copy(news = it)
                Log.d("RecordNewsViewModel", "loadSavedArticles: ${it.size}")
            }
        }
    }
}

data class RecordNewsUIState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
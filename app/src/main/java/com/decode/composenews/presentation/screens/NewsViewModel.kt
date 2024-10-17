package com.decode.composenews.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.usecase.GetNewsUseCase
import com.decode.composenews.domain.usecase.GetSearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchNewsUseCase: GetSearchNewsUseCase,
): ViewModel() {
    private val _selectedCategory = MutableStateFlow<String?>("sports")
    val selectedCategory= _selectedCategory.asStateFlow()

    private val _searchNews = MutableStateFlow<PagingData<News>>(PagingData.empty())
    val searchNews = _searchNews.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val newsPagingFlow: Flow<PagingData<News>> = selectedCategory.flatMapLatest { category ->
        getNewsUseCase(category).cachedIn(viewModelScope)
    }

    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _searchNews.value = PagingData.empty()
                return@launch
            } else {
                try {
                    getSearchNewsUseCase(query).cachedIn(viewModelScope).collect {
                        _searchNews.value = it
                    }
                } catch (e: Exception) {
                    Log.e("NewsViewModel", "Error: ${e.message}")
                }
            }
        }
    }
}

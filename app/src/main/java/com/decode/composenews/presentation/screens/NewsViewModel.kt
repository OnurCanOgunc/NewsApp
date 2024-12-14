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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchNewsUseCase: GetSearchNewsUseCase,
): ViewModel() {
    private val _selectedCategory = MutableStateFlow<String>("sports")
    val selectedCategory= _selectedCategory.asStateFlow()

    private val _searchNews = MutableStateFlow<PagingData<News>>(PagingData.empty())
    val searchNews = _searchNews.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val newsPagingFlow: Flow<PagingData<News>> = combine(selectedCategory, searchText) { category, query ->
        if (query.isNotEmpty()) {
            getSearchNewsUseCase(query, category).cachedIn(viewModelScope)
        } else {
            getNewsUseCase(category).cachedIn(viewModelScope)
        }
    }.flatMapLatest { it }

    fun setSelectedCategory(category: String?) {
        if (_selectedCategory.value != category) {
            _selectedCategory.value = category ?: "sports"
        }
    }

    fun setSearchText(text: String) {
        _searchText.value = text
    }
}

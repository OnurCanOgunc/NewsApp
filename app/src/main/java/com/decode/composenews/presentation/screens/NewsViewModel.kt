package com.decode.composenews.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.decode.composenews.domain.model.News
import com.decode.composenews.domain.usecase.GetNewsUseCase
import com.decode.composenews.domain.usecase.GetSearchNewsUseCase
import com.decode.composenews.presentation.screens.NewsContract.HomeUIEffect
import com.decode.composenews.presentation.screens.NewsContract.HomeUIEvent
import com.decode.composenews.presentation.screens.NewsContract.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchNewsUseCase: GetSearchNewsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState
        .onStart {
            _uiState.update { it.copy(news = newsPagingFlow) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUIState()
        )

    private val _uiEffect = MutableSharedFlow<HomeUIEffect>()
    val uiEffect: SharedFlow<HomeUIEffect> = _uiEffect

//    private val _selectedCategory = MutableStateFlow("sports")
//    private val _searchText = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val newsPagingFlow: Flow<PagingData<News>> =
        _uiState.map { it.searchText to it.selectedCategory }
            .flatMapLatest { (query, category) ->
                if (query.isNotEmpty()) {
                    getSearchNewsUseCase(query, category)
                } else {
                    getNewsUseCase(category)
                }
            }
            .catch { throwable ->
                handleError(throwable)
            }
            .cachedIn(viewModelScope)

    fun onEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.Search -> {
                _uiState.update { it.copy(searchText = event.query, news = newsPagingFlow) }
                //_searchText.value = event.query
            }

            is HomeUIEvent.SelectCategory -> {
                _uiState.update {
                    it.copy(selectedCategory = event.category, news = newsPagingFlow)
                }
                //_selectedCategory.value = event.category
            }

            HomeUIEvent.Refresh -> { refreshNews()}
        }
    }

    private fun refreshNews() {
        viewModelScope.launch {
            _uiState.update { it.copy(searchText = "",news = newsPagingFlow) }
            _uiEffect.emit(HomeUIEffect.ShowSnackbar("News refreshed"))
        }
    }

    private suspend fun handleError(throwable: Throwable) {
        _uiEffect.emit(HomeUIEffect.ShowSnackbar(throwable.message ?: "An error occurred"))
    }
}

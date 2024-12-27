package com.decode.composenews.presentation.screens.home

import androidx.paging.PagingData
import com.decode.composenews.domain.model.News
import kotlinx.coroutines.flow.Flow

object NewsContract {
    data class HomeUIState(
        val news: Flow<PagingData<News>>? = null,
        val searchText: String = "",
        val selectedCategory: String = "sports"
    )
    sealed class HomeUIEvent {
        data class Search(val query: String) : HomeUIEvent()
        data class SelectCategory(val category: String) : HomeUIEvent()
    }
    sealed class HomeUIEffect {
        data class ShowSnackbar(val message: String) : HomeUIEffect()
    }
}
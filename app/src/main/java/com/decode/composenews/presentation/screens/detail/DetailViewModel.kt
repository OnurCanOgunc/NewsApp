package com.decode.composenews.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.composenews.domain.usecase.GetArticle
import com.decode.composenews.presentation.screens.detail.DetailContract.DetailUIEvent
import com.decode.composenews.presentation.screens.detail.DetailContract.DetailUIState
import com.decode.composenews.util.NewsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getArticle: GetArticle) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUIState())
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()


    fun onEvent(event: DetailUIEvent) {
        when (event) {
            is DetailUIEvent.Load -> loadArticle(event.id)
            is DetailUIEvent.Save -> TODO()
            DetailUIEvent.Share -> TODO()
        }
    }

    private fun loadArticle(id: String) {
        if (_uiState.value.news != null){
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
}
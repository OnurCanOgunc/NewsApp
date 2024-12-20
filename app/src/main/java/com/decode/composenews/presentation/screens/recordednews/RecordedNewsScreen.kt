package com.decode.composenews.presentation.screens.recordednews

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.decode.composenews.presentation.screens.recordednews.components.RecordedNewsItem

@Composable
fun RecordedNewsScreen(
    modifier: Modifier = Modifier,
    articleViewModel: RecordNewsViewModel = hiltViewModel(),
    navigate: (String) -> Unit
) {
    val uiState by articleViewModel.uiState.collectAsStateWithLifecycle()
    Log.d("RecordedNewsScreen", "News loaded: ${uiState.news}")
    RecordedNewsItem(news = uiState.news, navigate = navigate, onSaveClick = {
        articleViewModel.saveArticle(it)
    })
}

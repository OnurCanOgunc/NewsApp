package com.decode.composenews.presentation.screens.recordednews

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.decode.composenews.presentation.screens.recordednews.components.RecordedNewsItem

@Composable
fun RecordedNewsScreen(
    modifier: Modifier = Modifier,
    articleViewModel: RecordNewsViewModel = hiltViewModel(),
    navigate: (String) -> Unit
) {
    val uiState by articleViewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.isError -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.errorMessage, color = Color.Red, fontSize = 16.sp)
            }
        }
        uiState.news.isNotEmpty() -> {
            RecordedNewsItem(news = uiState.news, navigate = navigate, onSaveClick = {
                articleViewModel.saveArticle(it)
                Log.d("TAG", "RecordedNewsScreen: $it")
            })
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No news found.", color = Color.Red, fontSize = 16.sp)
            }
        }
    }
}

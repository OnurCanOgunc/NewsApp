package com.decode.composenews.presentation.screens.recordednews

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.composenews.presentation.screens.recordednews.RecordNewsContract.RecordNewsUIEvent
import com.decode.composenews.presentation.screens.recordednews.RecordNewsContract.RecordNewsUIState
import com.decode.composenews.presentation.screens.recordednews.components.RecordedNewsItem

@Composable
fun RecordedNewsScreen(
    uiState: RecordNewsUIState,
    onEvent: (RecordNewsUIEvent) -> Unit,
    navigate: (String) -> Unit
) {

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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Recorded News",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                RecordedNewsItem(news = uiState.news, navigate = navigate, onSaveClick = {
                    onEvent(RecordNewsUIEvent.SaveArticle(it))
                    Log.d("TAG", "RecordedNewsScreen: $it")
                })
            }
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

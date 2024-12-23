package com.decode.composenews.presentation.screens.recordednews.components

import android.R.attr.contentDescription
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.composenews.domain.model.News
import com.decode.composenews.presentation.ui.theme.Accent
import com.decode.composenews.presentation.ui.theme.LightText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun RecordedNewsItem(
    modifier: Modifier = Modifier,
    news: List<News>?,
    navigate: (String) -> Unit,
    onSaveClick: (News) -> Unit
) {

    if (news == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No Saved News.", color = Color.Gray)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = news,
            key = { it.id },
        ) { item ->
            NewsItem(news = item, onSaveClick = onSaveClick, navigate = navigate)

        }
    }


}

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    news: News,
    onSaveClick: (News) -> Unit,
    navigate: (String) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .clickable {
                navigate(news.id)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.Create,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(12.dp)
            )
            Text(
                text = news.author,
                fontSize = 12.sp,
                color = LightText,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = news.title,
            fontSize = 16.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = news.category[0],
            color = Accent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = news.published,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = LightText,
            )
            IconButton(
                onClick = {
                    onSaveClick(news)
                    Log.d("TAG", "NewsItem: $news")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Bookmarks,
                    contentDescription = ""
                )
            }
        }
    }
}
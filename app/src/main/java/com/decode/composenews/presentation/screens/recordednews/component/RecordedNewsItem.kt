package com.decode.composenews.presentation.screens.recordednews.component

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
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun RecordedNewsItem(
    modifier: Modifier = Modifier,
    newsList: List<News>?,
    navigate: (String) -> Unit,
    onSaveClick: (News) -> Unit
) {


    if (newsList == null) {
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
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = newsList,
            key = { it.id },
        ) { item ->
            NewsItem(
                news = item,
                onSaveClick = onSaveClick,
                navigate = navigate,
            )
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
        HorizontalDivider()
    }
}
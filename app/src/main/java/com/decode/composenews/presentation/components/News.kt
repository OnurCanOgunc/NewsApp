package com.decode.composenews.presentation.components

import android.util.Log
import android.util.Log.e
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.decode.composenews.R
import com.decode.composenews.domain.model.News
import com.decode.composenews.presentation.ui.theme.Accent
import com.decode.composenews.presentation.ui.theme.LightText

@Composable
fun News(modifier: Modifier = Modifier, news: LazyPagingItems<News>?) {

    if (news == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No news available.", color = Color.Gray)
        }
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (news.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is LoadState.Error -> {
                Text(
                    text = "No results were found matching your search criteria.",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        count = news.itemCount,
                        key = news.itemKey { it.id },
                    ) { index ->
                        news[index]?.let {
                            NewsItem(news = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(modifier: Modifier = Modifier, news: News) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        AsyncImage(
            model = news.image,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(15.dp)),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = news.category[0],
                color = Accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = news.title,
                fontSize = 16.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )

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
                modifier = Modifier,
                text = news.published,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = LightText,
            )
        }


    }
}


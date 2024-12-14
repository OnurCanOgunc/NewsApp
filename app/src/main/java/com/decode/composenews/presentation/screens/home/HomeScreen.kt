package com.decode.composenews.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.decode.composenews.R
import com.decode.composenews.presentation.components.News
import com.decode.composenews.presentation.components.SearchBar
import com.decode.composenews.presentation.components.TendingNewsChip
import com.decode.composenews.presentation.screens.NewsViewModel
import com.decode.composenews.presentation.ui.theme.ExtraLightText
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val news = newsViewModel.newsPagingFlow.collectAsLazyPagingItems()
    val searchText by newsViewModel.searchText.collectAsState()

    if (news.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (news.loadState.refresh as LoadState.Error).error.message.orEmpty()
            )
        }
    }

    Column(
        modifier.padding(top = 16.dp)
    ) {
        HeaderContent()
        SearchBar(
            query = searchText,
            onQueryChanged = {
                newsViewModel.setSearchText(it)
            },
            modifier = Modifier.padding(top = 26.dp),
        )
        TendingNewsChip(
            onCategorySelected = { category ->
                newsViewModel.setSelectedCategory(category)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        News(news = news)
    }
}

@Composable
fun HeaderContent(
    modifier: Modifier = Modifier,
    name: String = "Onur Can Öğünç",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.img_user),
            contentDescription = "",
            modifier = Modifier.size(48.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(text = "Welcome", color = ExtraLightText)
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        BadgedBox(badge = {
            Badge {
                Text(text = "13", fontSize = 10.sp)
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.Notifications, contentDescription = "Notifications",
                modifier = Modifier.size(28.dp)
            )
        }

    }
}



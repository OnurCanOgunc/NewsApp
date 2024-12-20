package com.decode.composenews.presentation.screens.article

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decode.composenews.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.decode.composenews.domain.model.News
import com.decode.composenews.presentation.screens.article.component.CustomButton
import com.decode.composenews.util.shareNews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    newsId: String,
    viewModel: ArticleViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ArticleContract.ArticleUIEvent.Load(newsId))
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = 8.dp),
                title = {
                    Text(modifier = Modifier, text = "ABC-NEWS", fontStyle = FontStyle.Italic)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    Icon(
                        modifier = Modifier.size(34.dp),
                        imageVector = Icons.Filled.Abc,
                        contentDescription = "",
                        tint = Color.Red,
                    )
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.isError -> {
                Text("An error occurred while loading the news.", color = Color.Red, fontSize = 16.sp)
            }

            uiState.news != null -> {
                NewsContent(
                    modifier = Modifier.padding(innerPadding),
                    news = uiState.news!!,
                    context = context,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun NewsContent(
    modifier: Modifier = Modifier,
    news: News,
    context: Context,
    viewModel: ArticleViewModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = news.title,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif
        )
        AsyncImage(
            model = news.image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(0.5f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Red, fontSize = 12.sp)) {
                        append(news.published)
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp

                        )
                    ) {
                        append("| ${news.author}")
                    }
                },
            )
            CustomButton(
                text = "Share",
                icon = Icons.Outlined.Share,
                onClick = {
                    val newsTitle = news.title
                    val newsLink = news.url
                    context.shareNews(newsTitle, newsLink)
                })
            Spacer(modifier = Modifier.width(8.dp))
            CustomButton(
                text = "Save",
                icon = if (news.saved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                onClick = {
                    viewModel.onEvent(ArticleContract.ArticleUIEvent.Save(news))
                })
        }
        Text(
            text = news.description,
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ArticleScreen(
        newsId = "1",
        navigateUp = {}
    )
}
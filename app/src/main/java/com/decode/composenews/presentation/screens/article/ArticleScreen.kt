package com.decode.composenews.presentation.screens.article

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.composenews.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.decode.composenews.domain.model.News
import com.decode.composenews.presentation.screens.article.component.CustomButton
import com.decode.composenews.util.collectWithLifecyle
import com.decode.composenews.util.shareNews
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    newsId: String,
    uiState: ArticleContract.ArticleUIState,
    uiEffect: Flow<ArticleContract.ArticleUIEffect>,
    onEvent: (ArticleContract.ArticleUIEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        onEvent(ArticleContract.ArticleUIEvent.Load(newsId))
    }

    uiEffect.collectWithLifecyle { effect->
        when (effect) {
            is ArticleContract.ArticleUIEffect.SaveMessage -> {
                snackbarHostState.showSnackbar(message = effect.message,withDismissAction = true)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                    Text("An error occurred while loading the news.", color = Color.Red, fontSize = 16.sp)
                }
            }

            uiState.news != null -> {
                NewsContent(
                    modifier = Modifier.padding(innerPadding),
                    news = uiState.news,
                    context = context,
                    onEvent = onEvent

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
    onEvent: (ArticleContract.ArticleUIEvent) -> Unit
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
                icon = if (news.saved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                onClick = {
                    onEvent(ArticleContract.ArticleUIEvent.Save(news))
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
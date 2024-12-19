package com.decode.composenews.presentation.screens.detail

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
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
import androidx.compose.ui.graphics.RectangleShape
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
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    newsId: String,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(DetailContract.DetailUIEvent.Load(newsId))
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = 8.dp, shape = RectangleShape),
                title = {
                    Text(modifier = Modifier, text = "ABC-NEWS", fontStyle = FontStyle.Italic)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            //viewModel.onEvent(DetailContract.DetailUIEvent.Navigation)
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
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.news?.title ?: "",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
            AsyncImage(
                model =uiState.news?.image,
                contentDescription = null,
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
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("4hr ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("| US Canada")
                        }
                    },
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.clickable {
                        viewModel.onEvent(DetailContract.DetailUIEvent.Share)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                    Text(text = "Share", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    modifier = Modifier.clickable {
                        viewModel.onEvent(DetailContract.DetailUIEvent.Save(viewModel.uiState.value.news))
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                    Text(text = "Save", fontSize = 16.sp)
                }
            }
            Text(
                text = uiState.news?.description ?: "",
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        newsId = "1",
        navigateUp = {}
    )
}
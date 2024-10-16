package com.decode.composenews.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.composenews.ui.theme.Accent
import com.decode.composenews.R


@Composable
fun BreakingNews(
    modifier: Modifier = Modifier,
    textClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Breaking News", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = textClick) {
                Text(text = "See All", color = Accent)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        BreakingNewsItem()
        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Composable
fun BreakingNewsItem(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState { 13}

    HorizontalPager(
        state = pagerState,
        pageSpacing = 12.dp,
        contentPadding = PaddingValues(horizontal = 8.dp),

    ) { pageIndex ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Page out of green har tie me down down see",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(13) {
            val color = if (pagerState.currentPage == it) Color.Blue else Color.Gray
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(19.dp)
                    )
                    .size(width = 12.dp, height = 3.dp)
            )
        }
    }
}

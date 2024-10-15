package com.decode.composenews.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.composenews.R
import com.decode.composenews.ui.theme.ExtraLightText

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.padding(top = 10.dp)) {
        Column {
            HeaderContent()
        }
    }
}

@Composable
fun HeaderContent(
    modifier: Modifier = Modifier,
    name: String = "Onur Can Öğünç",
    userImage: Int = R.drawable.img_user
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(userImage),
            contentDescription = null,
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
        Icon(
            imageVector = Icons.Outlined.Notifications, contentDescription = "Notifications",
            modifier = Modifier.size(28.dp)
        )
    }
}
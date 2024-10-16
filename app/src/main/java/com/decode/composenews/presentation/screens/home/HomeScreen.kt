package com.decode.composenews.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decode.composenews.R
import com.decode.composenews.presentation.components.BreakingNews
import com.decode.composenews.presentation.components.TendingNewsChip
import com.decode.composenews.ui.theme.SearchBackground
import com.decode.composenews.ui.theme.ExtraLightText
import com.decode.composenews.ui.theme.IconTint

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    var searchText by remember { mutableStateOf("") }

        Column(
            modifier.padding(top = 16.dp)
        ) {
            HeaderContent()
            SearchBar(
                modifier = Modifier.padding(top = 26.dp),
                query = searchText,
                onQueryChanged = {
                    searchText = it
                },
                onSearch = {}
            )
            BreakingNews(textClick = {})
            TendingNewsChip()
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
            .padding(horizontal = 20.dp),
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

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = "let's look at the news today") },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearch(query)
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChanged("")
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        shape = RoundedCornerShape(13.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SearchBackground,
            focusedContainerColor = SearchBackground,
            focusedLeadingIconColor = IconTint,  // Odaklanıldığında ikon rengi
            unfocusedLeadingIconColor = IconTint,
            unfocusedIndicatorColor   = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedPlaceholderColor = ExtraLightText,
            focusedPlaceholderColor = ExtraLightText,
        )
    )
}
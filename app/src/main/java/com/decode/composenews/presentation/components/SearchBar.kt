package com.decode.composenews.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.decode.composenews.presentation.ui.theme.ExtraLightText
import com.decode.composenews.presentation.ui.theme.IconTint
import com.decode.composenews.presentation.ui.theme.SearchBackground

@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = "let's look at the news today") },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .focusRequester(focusRequester),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onQueryChanged(query)
                focusManager.clearFocus()
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
                    keyboardController?.hide()
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
            focusedLeadingIconColor = IconTint,
            unfocusedLeadingIconColor = IconTint,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedPlaceholderColor = ExtraLightText,
            focusedPlaceholderColor = ExtraLightText,
        )
    )
}
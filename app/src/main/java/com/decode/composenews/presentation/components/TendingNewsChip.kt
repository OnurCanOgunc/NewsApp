package com.decode.composenews.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.decode.composenews.ui.theme.Accent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TendingNewsChip(modifier: Modifier = Modifier) {
    val checkedItem = remember { mutableStateListOf<Int>(0) }
    val options = listOf<String>(
        "General",
        "Technology",
        "Sports",
        "Finance",
        "Politics",
        "World",
        "Regional",
        "Opinion",
        "Entertainment",
        "Movie"
    )
    val scrollState = rememberScrollState()

    MultiChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 2.dp)
    ) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                onCheckedChange = {
                    if (index in checkedItem) {
                        checkedItem.remove(index)
                    } else {
                        checkedItem.add(index)
                    }
                },
                checked = index in checkedItem,
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size,
                    baseShape = RoundedCornerShape(12.dp)
                ),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = Accent,
                    activeContentColor = Color.White,
                    activeBorderColor = Color.Transparent,
                )
            ) {
                Text(text = option)
            }
        }
    }
}
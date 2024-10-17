package com.decode.composenews.presentation.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.decode.composenews.presentation.ui.theme.Accent

data class NavItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun BottomAppBar(modifier: Modifier = Modifier) {

    val items = listOf(
        NavItemState(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavItemState(
            title = "Explorer",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
        ),
        NavItemState(
            title = "Account",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face,
        )
    )

    var selected by remember {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = modifier
            .padding(horizontal = 12.dp,vertical = 8.dp)
            .clip(RoundedCornerShape(25.dp, 25.dp, 25.dp, 25.dp)),
        tonalElevation = 4.dp,
        windowInsets = WindowInsets.captionBar
    ) {
        items.forEachIndexed { index, bottomItem ->

            NavigationBarItem(
                modifier = Modifier,
                selected = index == selected,
                onClick = dropUnlessResumed {
                    selected = index
                },
                icon = {
                    Icon(
                        imageVector = if (selected == index) bottomItem.selectedIcon
                        else bottomItem.unselectedIcon,
                        contentDescription = bottomItem.title,
                    )
                },
                alwaysShowLabel = false,
                label = {
                    Text(text = bottomItem.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Accent,
                    selectedTextColor = Accent,
                )
            )

        }
    }
}
package com.decode.composenews.presentation.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.automirrored.outlined.Feed
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.decode.composenews.presentation.navigation.Screen
import com.decode.composenews.presentation.ui.theme.Accent

data class NavItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Screen
)

@Composable
fun BottomAppBar(modifier: Modifier = Modifier, navController: NavController) {

    val items = listOf(
        NavItemState(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.Home.News
        ),
        NavItemState(
            title = "Feed News",
            selectedIcon = Icons.AutoMirrored.Filled.Feed,
            unselectedIcon = Icons.AutoMirrored.Outlined.Feed,
            route = Screen.FeedNews
        ),
        NavItemState(
            title = "Recorded News",
            selectedIcon = Icons.Filled.Bookmarks,
            unselectedIcon = Icons.Outlined.Bookmarks,
            route = Screen.RecordedNews
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: Screen.Home.News::class.qualifiedName.orEmpty()

//    argümanlı bir route yok
//    val currentRouteTrimmed by remember(currentRoute) {
//        derivedStateOf { currentRoute.substringBefore("?") }
//    }

    val isVisible = rememberSaveable(navBackStackEntry) {
        navBackStackEntry?.destination?.route == Screen.Home.News::class.qualifiedName ||
                navBackStackEntry?.destination?.route == Screen.FeedNews::class.qualifiedName ||
                navBackStackEntry?.destination?.route == Screen.RecordedNews::class.qualifiedName
    }

    if (isVisible) {
        NavigationBar(
            modifier = modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(25.dp, 25.dp, 25.dp, 25.dp)),
            tonalElevation = 4.dp,
            windowInsets = WindowInsets.navigationBars
        ) {
            items.forEachIndexed { _, bottomItem ->
                val isSelected by remember(currentRoute) {
                    derivedStateOf { currentRoute == bottomItem.route::class.qualifiedName }
                }
                NavigationBarItem(
                    modifier = Modifier,
                    selected = isSelected,
                    onClick = dropUnlessResumed {
                        navController.navigate(bottomItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) bottomItem.selectedIcon
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
                        indicatorColor = Accent.copy(alpha = 0.2f),
                    )
                )

            }
        }
    }
}
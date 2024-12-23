package com.decode.composenews.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.decode.composenews.presentation.components.BottomAppBar
import com.decode.composenews.presentation.navigation.nav_graph.bottom

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    Scaffold(
        bottomBar = { BottomAppBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Home
        ) {
            bottom(navController)
        }
    }

}
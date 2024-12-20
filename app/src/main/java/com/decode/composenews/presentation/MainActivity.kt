package com.decode.composenews.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.decode.composenews.presentation.navigation.NavGraph
import com.decode.composenews.presentation.ui.theme.ComposeNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                val navController = rememberNavController()

                NavGraph(navController = navController)

            }
        }
    }
}

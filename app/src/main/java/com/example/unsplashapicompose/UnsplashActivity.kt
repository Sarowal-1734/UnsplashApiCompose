package com.example.unsplashapicompose

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unsplashapicompose.navigation.MainDirections
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.NuportMainScreen
import com.example.unsplashapicompose.ui.theme.UnsplashApiComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var navigationManager: NavigationManager = NavigationManager()

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalFoundationApi::class,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = android.graphics.Color.WHITE
        setContent {
            val navController = rememberNavController()
            UnsplashApiComposeTheme {
                Box {
                    NavHost(
                        navController,
                        startDestination = MainDirections.main.destination
                    ) {
                        composable(MainDirections.main.destination) {
                            NuportMainScreen(navigationManager)
                        }
                    }
                }
            }
            navigationManager.commands.collectAsState().value.also { command ->
                if (command.destination.isNotEmpty()) {
                    when (command.destination) {
                        MainDirections.main.destination -> {
                            navController.navigate(command.destination) {
                                navController.currentDestination?.route?.let {
                                    popUpTo(it) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
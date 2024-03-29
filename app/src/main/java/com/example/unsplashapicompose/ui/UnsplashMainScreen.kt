package com.example.unsplashapicompose.ui

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotosViewModel
import com.example.unsplashapicompose.ui.unsplash_photos.screens.UnsplashPhotoDetailsScreen
import com.example.unsplashapicompose.ui.unsplash_photos.screens.UnsplashPhotosScreen

sealed class Screens(val route: String) {
    object UnsplashPhotos :
        Screens("unsplash-photos")

    object UnsplashPhotoDetails :
        Screens(
            "unsplash-photo-details/{userName}"
        ) {
        fun createRoute(userName: String): String {
            return "unsplash-photo-details/${userName}"
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun UnsplashMainScreen(navigationManager: NavigationManager) {
    val navController = rememberNavController()
    val (_, setIsNested) = rememberSaveable { mutableStateOf(false) }
    val unsplashPhotosViewModel = hiltViewModel<UnsplashPhotosViewModel>()

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = Screens.UnsplashPhotos.route
            ) {
                composable(Screens.UnsplashPhotos.route) {
                    val uiState by unsplashPhotosViewModel.uiState.collectAsState()
                    UnsplashPhotosScreen(
                        viewState = uiState,
                        events = unsplashPhotosViewModel::handleEvent
                    )
                }
                composable(Screens.UnsplashPhotoDetails.route) { backStackEntry ->
                    val userName = backStackEntry.arguments?.getString("userName")
                    val uiState by unsplashPhotosViewModel.uiState.collectAsState()
                    UnsplashPhotoDetailsScreen(
                        userName = userName,
                        viewState = uiState,
                        onBack = { navController.popBackStack() },
                        events = unsplashPhotosViewModel::handleEvent
                    )
                }
            }
        }
    }
    navigationManager.commands.collectAsState().value.also { command ->
        if (command.destination.isNotEmpty()) {
            when {
                command.destination == Screens.UnsplashPhotoDetails.route ||
                        command.destination.startsWith("unsplash-photo-details") -> {
                    setIsNested(true)
                    navController.navigate(command.destination) {
                        launchSingleTop = true
                    }
                }
                else -> {
                    try {
                        navController.navigate(command.destination) {
                            popUpTo(command.destination) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        setIsNested(false)
                    } catch (e: Exception) {
                        Log.i("Navigation", "Unrecognized route.")
                    }
                }
            }
        }
    }
}
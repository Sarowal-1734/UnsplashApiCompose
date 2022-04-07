package com.example.unsplashapicompose.navigation

import androidx.navigation.NamedNavArgument
import com.example.unsplashapicompose.ui.Screens

object HomeDirections {

    val root = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "home"
    }

    val unsplashPhotosDetails = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = Screens.UnsplashPhotoDetails.route
    }
}
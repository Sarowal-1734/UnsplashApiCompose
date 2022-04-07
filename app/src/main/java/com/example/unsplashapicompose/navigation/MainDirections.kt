package com.example.unsplashapicompose.navigation

import androidx.navigation.NamedNavArgument

object MainDirections {

    val main = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "main"
    }

    val default = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }
}
package com.example.unsplashapicompose.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var commands = MutableStateFlow(MainDirections.main)

    fun navigate(direction: NavigationCommand) {
        commands.value = direction
    }
}
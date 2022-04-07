package com.example.unsplashapicompose

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unsplashapicompose.managers.ConnectionManager
import com.example.unsplashapicompose.navigation.MainDirections
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.UnsplashMainScreen
import com.example.unsplashapicompose.ui.theme.Primary
import com.example.unsplashapicompose.ui.theme.UnsplashApiComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var navigationManager: NavigationManager = NavigationManager()

    @Inject
    lateinit var connectionManager: ConnectionManager

    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalFoundationApi::class,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = android.graphics.Color.WHITE
        if (!connectionManager.isConnected()) {
            val dialog = AlertDialog.Builder(this)
                .setTitle("No Network Connection")
                .setMessage("Please ensure you are connected to the internet.")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    finish()
                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Primary.toArgb())
            return
        }
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
            connectionManager.connectionLiveData.observe(this) {
                if (!it) {
                    lifecycleScope.launch {
                        snackbarHostState.value.showSnackbar(
                            message = "No Network Connection",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
            UnsplashApiComposeTheme {
                Box {
                    NavHost(
                        navController,
                        startDestination = MainDirections.main.destination
                    ) {
                        composable(MainDirections.main.destination) {
                            UnsplashMainScreen(navigationManager)
                        }
                    }
                    SnackbarHost(
                        hostState = snackbarHostState.value,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        Snackbar(
                            modifier = Modifier.padding(12.dp),
                            backgroundColor = Color.White,
                            snackbarData = it,
                            contentColor = Color.Black
                        )
                    }
                }
            }
            navigationManager.commands.collectAsState().value.also { command ->
                Log.e("ASD", "1")
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
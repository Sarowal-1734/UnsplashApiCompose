package com.example.unsplashapicompose

import android.os.Build
import android.os.Bundle
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.unsplashapicompose.managers.ConnectionManager
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.UnsplashMainScreen
import com.example.unsplashapicompose.ui.theme.Primary
import com.example.unsplashapicompose.ui.theme.UnsplashApiComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

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
                    UnsplashMainScreen(navigationManager)
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
        }
    }
}
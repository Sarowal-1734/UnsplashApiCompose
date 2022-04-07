package com.example.unsplashapicompose.ui.unsplash_photos

import androidx.navigation.NamedNavArgument
import com.example.unsplashapicompose.data.repositories.UnsplashPhotosRepository
import com.example.unsplashapicompose.navigation.NavigationCommand
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.BaseViewModel
import com.example.unsplashapicompose.ui.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UnsplashPhotosViewModel @Inject constructor(
    private val repository: UnsplashPhotosRepository,
    private val navigationManager: NavigationManager
) : BaseViewModel<UnsplashPhotoEvent>() {

    private val _uiState = MutableStateFlow(UnsplashPhotoState())
    val uiState: StateFlow<UnsplashPhotoState> = _uiState

    override fun handleEvent(event: UnsplashPhotoEvent) {
        when (event) {
            is UnsplashPhotoEvent.FetchUnsplashPhotos -> {
                val query = if (event.query.isNullOrEmpty()) "Cat" else event.query
                searchPhotos(query)
            }
            is UnsplashPhotoEvent.SearchQueryChangedAcknowledged -> {
                _uiState.value = uiState.value.build {
                    this.searchQuery = event.query
                }
            }
            is UnsplashPhotoEvent.ViewDeliveryPlan -> {
                navigationManager.navigate(object : NavigationCommand {
                    override val arguments = emptyList<NamedNavArgument>()
                    override val destination =
                        Screens.UnsplashPhotoDetailsScreen.createRoute(event.id)
                })
            }
            else -> {
                // No-op
            }
        }
    }

    private fun searchPhotos(query: String) {
        val unsplashPhotos = repository.getUnsplashPhotoStream(query)
        _uiState.value = uiState.value.build {
            this.unsplashPhotosResults = unsplashPhotos
        }
    }

}
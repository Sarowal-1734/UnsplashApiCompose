package com.example.unsplashapicompose.ui.unsplash_photos

import androidx.navigation.NamedNavArgument
import androidx.paging.PagingData
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import com.example.unsplashapicompose.data.repositories.UnsplashPhotosRepository
import com.example.unsplashapicompose.navigation.NavigationCommand
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.BaseViewModel
import com.example.unsplashapicompose.ui.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
            is UnsplashPhotoEvent.ViewUnsplashPhoto -> {
                navigationManager.navigate(object : NavigationCommand {
                    override val arguments = emptyList<NamedNavArgument>()
                    override val destination =
                        Screens.UnsplashPhotoDetails.createRoute(event.url)
                })
            }
            else -> {
                // No-op
            }
        }
    }

    private fun applyEvents(
        paging: PagingData<UnsplashPhoto>,
        event: UnsplashPhotoEvent
    ): PagingData<UnsplashPhoto> {
        return paging
    }

    private fun searchPhotos(query: String) {
        val modificationEvents = MutableStateFlow<List<UnsplashPhotoEvent>>(emptyList())
        val unsplashPhotos = repository.getUnsplashPhotoStream(query)
            .combine(modificationEvents) { pagingData, modifications ->
                modifications.fold(pagingData) { acc, event ->
                    applyEvents(acc, event)
                }
            }
        _uiState.value = uiState.value.build {
            this.unsplashPhotosResults = unsplashPhotos
        }
    }

}
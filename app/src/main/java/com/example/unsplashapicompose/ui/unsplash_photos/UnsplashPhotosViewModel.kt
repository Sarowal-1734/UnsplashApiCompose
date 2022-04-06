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
    repository: UnsplashPhotosRepository,
    private val navigationManager: NavigationManager
) : BaseViewModel<UnsplashPhotoEvent>() {

    private val modificationEvents = MutableStateFlow<List<UnsplashPhotoEvent>>(emptyList())

    private val unsplashPhotosResult = repository.getUnsplashPhotoStream("Cute Baby")
        .combine(modificationEvents) { pagingData, modifications ->
            modifications.fold(pagingData) { acc, _ ->
                applyUnsplashPhotoEvents(acc)
            }
        }

    private val _uiState = MutableStateFlow(
        UnsplashPhotoState(
            unsplashPhotosResult = unsplashPhotosResult
        )
    )
    val uiState: StateFlow<UnsplashPhotoState> = _uiState

    override fun handleEvent(event: UnsplashPhotoEvent) {
        when (event) {
            is UnsplashPhotoEvent.FetchUnsplashPhotos -> {
                searchPhotos(event.query)
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

    private fun applyUnsplashPhotoEvents(
        paging: PagingData<UnsplashPhoto>
    ): PagingData<UnsplashPhoto> {
        return paging
    }

    private fun searchPhotos(query: String? = null) {

    }

}
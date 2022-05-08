package com.example.unsplashapicompose.ui.unsplash_photos

import androidx.lifecycle.viewModelScope
import androidx.navigation.NamedNavArgument
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import com.example.unsplashapicompose.data.repositories.UnsplashPhotosRepository
import com.example.unsplashapicompose.navigation.NavigationCommand
import com.example.unsplashapicompose.navigation.NavigationManager
import com.example.unsplashapicompose.ui.BaseViewModel
import com.example.unsplashapicompose.ui.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnsplashPhotosViewModel @Inject constructor(
    private val repository: UnsplashPhotosRepository,
    private val navigationManager: NavigationManager
) : BaseViewModel<UnsplashPhotoEvent>() {

    private var searchUnsplashPhotosJob: Job? = null

    private val _uiState = MutableStateFlow(UnsplashPhotoState())
    val uiState: StateFlow<UnsplashPhotoState> = _uiState

    override fun handleEvent(event: UnsplashPhotoEvent) {
        when (event) {
            is UnsplashPhotoEvent.FetchUnsplashPhotos -> {
                val query = event.query.ifEmpty { "Cat" }
                searchPhotos(query)
            }
            is UnsplashPhotoEvent.SearchQueryChangedAcknowledged -> {
                searchUnsplashPhotosJob?.cancel()
                searchUnsplashPhotosJob = viewModelScope.launch {
                    delay(500)
                    _uiState.value = uiState.value.build {
                        this.searchQuery = event.query
                    }
                }
            }
            is UnsplashPhotoEvent.ViewUnsplashPhoto -> {
                _uiState.value = uiState.value.build {
                    this.unsplashPhoto = event.unsplashPhoto
                }
                navigationManager.navigate(object : NavigationCommand {
                    override val arguments = emptyList<NamedNavArgument>()
                    override val destination =
                        Screens.UnsplashPhotoDetails.createRoute(event.unsplashPhoto?.user?.name!!)
                })
            }
        }
    }

    private fun applyEvents(
        paging: PagingData<UnsplashPhoto>
    ): PagingData<UnsplashPhoto> {
        return paging
    }

    private fun searchPhotos(query: String) {
        val modificationEvents = MutableStateFlow<List<UnsplashPhotoEvent>>(emptyList())
        val unsplashPhotos = repository.getUnsplashPhotoStream(query)
            .cachedIn(viewModelScope)
            .combine(modificationEvents) { pagingData, modifications ->
                modifications.fold(pagingData) { acc, _ ->
                    applyEvents(acc)
                }
            }
        _uiState.value = uiState.value.build {
            this.unsplashPhotosResults = unsplashPhotos
        }
    }
}
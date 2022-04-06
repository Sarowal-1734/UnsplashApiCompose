package com.example.unsplashapicompose.ui.unsplash_photos

import androidx.paging.PagingData
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow

class UnsplashPhotoState(
    val unsplashPhotosResult: Flow<PagingData<UnsplashPhoto>>? = null,
    var searchQuery: String = "Cute Baby",
    val isSearchingPhotos: Boolean = false
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: UnsplashPhotoState) {
        var unsplashPhotosResult = state.unsplashPhotosResult
        var searchQuery = state.searchQuery
        var isSearchingPhotos = state.isSearchingPhotos

        fun build(): UnsplashPhotoState {
            return UnsplashPhotoState(
                unsplashPhotosResult,
                searchQuery,
                isSearchingPhotos
            )
        }
    }
}
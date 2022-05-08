package com.example.unsplashapicompose.ui.unsplash_photos

import androidx.paging.PagingData
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow

class UnsplashPhotoState(
    val unsplashPhotosResult: Flow<PagingData<UnsplashPhoto>>? = null,
    val searchQuery: String = "",
    val unsplashPhoto: UnsplashPhoto? = null,
    val isSearchingPhotos: Boolean = false
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: UnsplashPhotoState) {
        var unsplashPhotosResults = state.unsplashPhotosResult
        var searchQuery = state.searchQuery
        var unsplashPhoto = state.unsplashPhoto
        var isSearchingPhotos = state.isSearchingPhotos

        fun build(): UnsplashPhotoState {
            return UnsplashPhotoState(
                unsplashPhotosResults,
                searchQuery,
                unsplashPhoto,
                isSearchingPhotos
            )
        }
    }
}
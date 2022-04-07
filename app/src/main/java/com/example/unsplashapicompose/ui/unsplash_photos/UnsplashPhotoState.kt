package com.example.unsplashapicompose.ui.unsplash_photos

import androidx.paging.PagingData
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow

class UnsplashPhotoState(
    val unsplashPhotosResult: ArrayList<UnsplashPhoto>? = ArrayList(),
    val unsplashPhotosResults: Flow<PagingData<UnsplashPhoto>>? = null,
    var searchQuery: String? = "",
    val isSearchingPhotos: Boolean = false
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: UnsplashPhotoState) {
        var unsplashPhotosResult = state.unsplashPhotosResult
        var unsplashPhotosResults = state.unsplashPhotosResults
        var searchQuery = state.searchQuery
        var isSearchingPhotos = state.isSearchingPhotos

        fun build(): UnsplashPhotoState {
            return UnsplashPhotoState(
                unsplashPhotosResult,
                unsplashPhotosResults,
                searchQuery,
                isSearchingPhotos
            )
        }
    }
}
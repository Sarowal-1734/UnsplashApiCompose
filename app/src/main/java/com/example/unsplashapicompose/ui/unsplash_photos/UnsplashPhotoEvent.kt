package com.example.unsplashapicompose.ui.unsplash_photos

import com.example.unsplashapicompose.data.model.UnsplashPhoto


sealed class UnsplashPhotoEvent {

    data class FetchUnsplashPhotos(val query: String = "") : UnsplashPhotoEvent()

    data class SearchQueryChangedAcknowledged(val query: String = "") : UnsplashPhotoEvent()

    data class ViewUnsplashPhoto(val unsplashPhoto: UnsplashPhoto?) : UnsplashPhotoEvent()

}
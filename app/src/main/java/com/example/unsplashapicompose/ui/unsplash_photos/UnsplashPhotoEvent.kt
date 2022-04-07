package com.example.unsplashapicompose.ui.unsplash_photos


sealed class UnsplashPhotoEvent {

    data class ViewDeliveryPlan(val id: String) : UnsplashPhotoEvent()

    data class FetchUnsplashPhotos(val query: String? = "") : UnsplashPhotoEvent()

    data class SearchQueryChangedAcknowledged(val query: String? = "") : UnsplashPhotoEvent()

}
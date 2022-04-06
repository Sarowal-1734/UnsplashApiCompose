package com.example.unsplashapicompose.ui.unsplash_photos.screens

import androidx.compose.runtime.Composable
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotoState
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotoEvent

@Composable
fun UnsplashPhotoDetailsScreen(
    id: String? = null,
    onBack: () -> Unit = {},
    viewState: UnsplashPhotoState = UnsplashPhotoState(),
    events: (event: UnsplashPhotoEvent) -> Unit = {}
) {

}
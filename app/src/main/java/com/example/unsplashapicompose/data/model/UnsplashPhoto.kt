package com.example.unsplashapicompose.data.model

data class UnsplashPhoto(
    val id: String,
    val user: User,
    val urls: Urls,
    val description: String,
    val alt_description: String,
    val updated_at: String
)
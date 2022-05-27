package com.example.unsplashapicompose.ui.unsplash_photos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.unsplashapicompose.ui.components.TopBar
import com.example.unsplashapicompose.ui.theme.Background
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotoEvent
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotoState

@Composable
fun UnsplashPhotoDetailsScreen(
    userName: String? = null,
    onBack: () -> Unit = {},
    viewState: UnsplashPhotoState = UnsplashPhotoState(),
    events: (event: UnsplashPhotoEvent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
    ) {
        TopBar(
            title = "$userName",
            onButtonClicked = { onBack() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = Background)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                elevation = 5.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(viewState.unsplashPhoto?.urls?.regular),
                    contentDescription = "Unsplash Imagasdsadfe",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = viewState.unsplashPhoto?.description ?: "আমার সোনার বাংলা আমি তোমায়",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = viewState.unsplashPhoto?.alt_description ?: "তোমায়",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}
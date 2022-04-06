package com.example.unsplashapicompose.ui.unsplash_photos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberAsyncImagePainter
import com.example.unsplashapicompose.R
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import com.example.unsplashapicompose.ui.theme.Background
import com.example.unsplashapicompose.ui.theme.Primary
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotoEvent
import com.example.unsplashapicompose.ui.unsplash_photos.UnsplashPhotoState

@Composable
fun UnsplashPhotosScreen(
    viewState: UnsplashPhotoState = UnsplashPhotoState(),
    events: (event: UnsplashPhotoEvent) -> Unit = {}
) {
    val unsplashPhotos = viewState.unsplashPhotosResult?.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    shape = RoundedCornerShape(16.dp)
                    shadowElevation = 4.dp.toPx()
                    clip = true
                }
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 16.dp),
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            singleLine = true,
            value = viewState.searchQuery,
            onValueChange = {
                viewState.searchQuery = it
            },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box {
                        if (viewState.searchQuery.isEmpty()) {
                            Text(
                                text = "Search...",
                                color = Gray,
                                modifier = Modifier
                                    .height(16.dp),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        onClick = { unsplashPhotos?.refresh() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .clip(CircleShape)
                                .align(Alignment.CenterEnd),
                            tint = Primary
                        )
                    }
                }
            }
        )
        if (unsplashPhotos != null) {
            LazyColumn {
                itemsIndexed(
                    items = unsplashPhotos
                ) { index, item ->
                    UnsplashPhotoItem(index, item, events)
                }
            }
            unsplashPhotos.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        LinearProgressIndicator(
                            backgroundColor = Color.White,
                            color = Primary,
                            modifier = Modifier.padding(top = 64.dp)
                        )
                    }
                    loadState.append is LoadState.Error -> {
                        // TODO
                    }
                }
            }
        }
    }
}

@Composable
fun UnsplashPhotoItem(
    index: Int = 0,
    unsplashPhotos: UnsplashPhoto? = null,
    events: (event: UnsplashPhotoEvent) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(unsplashPhotos?.urls?.regular),
            contentDescription = "Unsplash Image",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    events(UnsplashPhotoEvent.ViewDeliveryPlan(unsplashPhotos!!.id))
                },
            contentScale = ContentScale.Crop
        )
    }
}
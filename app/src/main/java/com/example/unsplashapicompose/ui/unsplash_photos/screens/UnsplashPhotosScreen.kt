package com.example.unsplashapicompose.ui.unsplash_photos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    LaunchedEffect(Unit, viewState.searchQuery) {
        events(UnsplashPhotoEvent.FetchUnsplashPhotos(viewState.searchQuery))
    }

    val (searchQuery, setSearchQuery) = rememberSaveable { mutableStateOf("") }
    val (isVisibleNoSearchResult, setIsVisibleNoSearchResult) = rememberSaveable {
        mutableStateOf(
            false
        )
    }
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
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    shape = RoundedCornerShape(16.dp)
                    shadowElevation = 4.dp.toPx()
                    clip = true
                }
                .background(
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 16.dp),
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            singleLine = true,
            value = searchQuery,
            onValueChange = {
                setIsVisibleNoSearchResult(false)
                setSearchQuery(it)
                events(UnsplashPhotoEvent.SearchQueryChangedAcknowledged(it))
            },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box {
                        if (searchQuery.isEmpty()) {
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
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterEnd),
                            onClick = {
                                setIsVisibleNoSearchResult(false)
                                focusManager.clearFocus()
                                setSearchQuery("")
                                events(UnsplashPhotoEvent.SearchQueryChangedAcknowledged(""))
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = "Search Icon",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .align(Alignment.CenterEnd),
                                tint = Gray
                            )
                        }
                    }
                }
            }
        )
        if (unsplashPhotos != null) {
            LazyColumn(state = listState) {
                if (listState.isScrollInProgress) {
                    focusManager.clearFocus()
                }
                itemsIndexed(
                    items = unsplashPhotos
                ) { _, item ->
                    UnsplashPhotoItem(item, events)
                }
            }
            unsplashPhotos.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        LinearProgressIndicator(
                            backgroundColor = White,
                            color = Primary,
                            modifier = Modifier.padding(top = 64.dp)
                        )
                        setIsVisibleNoSearchResult(true)
                    }
                    loadState.refresh is LoadState.NotLoading && isVisibleNoSearchResult && unsplashPhotos.itemSnapshotList.isEmpty() -> {
                        Text(
                            modifier = Modifier.padding(top = 96.dp),
                            text = "No Result",
                            fontSize = 24.sp,
                            color = Gray,
                            fontWeight = FontWeight.SemiBold
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
    unsplashPhoto: UnsplashPhoto? = null,
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
            painter = rememberAsyncImagePainter(unsplashPhoto?.urls?.regular),
            contentDescription = "Unsplash Image",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    events(UnsplashPhotoEvent.ViewUnsplashPhoto(unsplashPhoto))
                },
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Transparent, Black),
                        startY = 400f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "${unsplashPhoto?.user?.name}",
                style = TextStyle(color = White, fontSize = 15.sp)
            )
        }
    }
}
package com.example.unsplashapicompose.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplashapicompose.api.UnsplashService
import com.example.unsplashapicompose.data.BaseRepository
import com.example.unsplashapicompose.data.model.UnsplashPhoto
import com.example.unsplashapicompose.data.page_sources.UnsplashPhotosPageSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashPhotosRepository @Inject constructor(
    private val service: UnsplashService
) : BaseRepository() {

    fun getUnsplashPhotoStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = UnsplashPhotosPageSource.DEFAULT_PAGE_SIZE,
                prefetchDistance = 15,
                initialLoadSize = UnsplashPhotosPageSource.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = { UnsplashPhotosPageSource(service, query) }
        ).flow
    }
}
package com.example.unsplashapicompose.data.page_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashapicompose.api.UnsplashService
import com.example.unsplashapicompose.data.model.UnsplashPhoto

class UnsplashPhotosPageSource(
    private val unsplashService: UnsplashService,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: 0
        return try {
            val response =
                unsplashService.getUnsplashPhotos(query, page, DEFAULT_PAGE_SIZE)
            val unsplashPhotos = response.results
            val lastPage = unsplashPhotos.size < DEFAULT_PAGE_SIZE
            LoadResult.Page(
                data = unsplashPhotos,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (lastPage) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }

}
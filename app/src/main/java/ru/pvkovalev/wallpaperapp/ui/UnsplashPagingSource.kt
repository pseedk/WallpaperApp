package ru.pvkovalev.wallpaperapp.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.pvkovalev.wallpaperapp.data.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.data.server.UnsplashApi

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi
) : PagingSource<Int, ResponseUnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseUnsplashPhoto>) =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseUnsplashPhoto> =
        try {
            val page = params.key ?: 1
            val photos = unsplashApi.getPhotos(page = page)
            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}
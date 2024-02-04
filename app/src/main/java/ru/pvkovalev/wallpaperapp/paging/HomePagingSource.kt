package ru.pvkovalev.wallpaperapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.pvkovalev.wallpaperapp.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.networking.RetrofitServices

class HomePagingSource(
    private val apiService: RetrofitServices
) : PagingSource<Int, ResponseUnsplashPhoto>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseUnsplashPhoto>) =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseUnsplashPhoto> =
        try {
            val page = params.key ?: FIRST_PAGE_INDEX
            val photos = apiService.getPhotos(page = page)
            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}
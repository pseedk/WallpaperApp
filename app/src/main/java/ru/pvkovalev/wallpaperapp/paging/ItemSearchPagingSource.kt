package ru.pvkovalev.wallpaperapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.pvkovalev.wallpaperapp.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.networking.RetrofitServices

class ItemSearchPagingSource(
    private val apiService: RetrofitServices,
    private val query: String
) : PagingSource<Int, ResponseUnsplashPhoto>() {

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseUnsplashPhoto>) =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseUnsplashPhoto> =
        try {
            val page = params.key ?: FIRST_PAGE_INDEX
            val photos = apiService.searchPhotos(query, page = page)
            LoadResult.Page(
                data = photos.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

}
package ru.pvkovalev.wallpaperapp.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ru.pvkovalev.wallpaperapp.data.api.paging.CategoryPagingSource
import ru.pvkovalev.wallpaperapp.data.api.paging.HomePagingSource
import ru.pvkovalev.wallpaperapp.data.api.paging.SearchPagingSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getHomeWallpapers() =
        Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                HomePagingSource(apiService = apiService)
            }
        ).flow

    fun getCategoryWallpapers(query: String) =
        Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                CategoryPagingSource(apiService = apiService, query = query)
            }
        ).flow

    fun searchWallpapers(query: String) =
        Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                SearchPagingSource(apiService = apiService, query = query)
            }
        ).flow
}
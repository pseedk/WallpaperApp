package ru.pvkovalev.wallpaperapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.pvkovalev.wallpaperapp.data.api.model.ResponseUnsplashPhoto

interface ApiRepository {

    fun getHomeWallpapers(): Flow<PagingData<ResponseUnsplashPhoto>>

    fun getCategoryWallpapers(query: String): Flow<PagingData<ResponseUnsplashPhoto>>

    fun searchWallpapers(query: String): Flow<PagingData<ResponseUnsplashPhoto>>
}
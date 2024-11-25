package ru.pvkovalev.wallpaperapp.data.api.repository

import ru.pvkovalev.wallpaperapp.data.api.RemoteDataSource
import ru.pvkovalev.wallpaperapp.domain.repository.ApiRepository
import ru.pvkovalev.wallpaperapp.utils.BaseApiResponse
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse(), ApiRepository {

    override fun getHomeWallpapers() = remoteDataSource.getHomeWallpapers()

    override fun getCategoryWallpapers(query: String) =
        remoteDataSource.getCategoryWallpapers(query)

    override fun searchWallpapers(query: String) = remoteDataSource.searchWallpapers(query)

}
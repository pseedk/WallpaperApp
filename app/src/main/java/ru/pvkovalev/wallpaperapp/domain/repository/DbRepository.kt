package ru.pvkovalev.wallpaperapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem

interface DbRepository {

    fun getWallpapers(): Flow<List<WallpaperItem>>

    suspend fun addWallpaper(wallpaperItem: WallpaperItem)

    suspend fun deleteWallpaper(wallpaperItem: WallpaperItem)

}
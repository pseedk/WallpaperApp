package ru.pvkovalev.wallpaperapp.db

import androidx.lifecycle.LiveData

class WallpaperRepository(private val wallpaperDao: WallpaperDao) {

    val getAllWallpapers: LiveData<List<WallpaperEntity>> = wallpaperDao.getAllWallpapers()

    suspend fun addWallpaper(wallpaperEntity: WallpaperEntity) {
        wallpaperDao.addWallpaper(wallpaperEntity)
    }
}
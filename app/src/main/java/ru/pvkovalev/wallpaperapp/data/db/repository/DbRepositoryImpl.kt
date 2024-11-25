package ru.pvkovalev.wallpaperapp.data.db.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.pvkovalev.wallpaperapp.data.db.WallpaperDao
import ru.pvkovalev.wallpaperapp.data.mapper.WallpapersDbMapper
import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem
import ru.pvkovalev.wallpaperapp.domain.repository.DbRepository

class DbRepositoryImpl(
    private val wallpaperDao: WallpaperDao,
    private val mapper: WallpapersDbMapper
) : DbRepository {

    override fun getWallpapers(): Flow<List<WallpaperItem>> =
        wallpaperDao.getAllWallpapers().map {
            mapper.mapListDbModelToListEntity(it)
        }

    override suspend fun addWallpaper(wallpaperItem: WallpaperItem) {
        wallpaperDao.addWallpaper(mapper.mapEntityToDbModel(wallpaperItem))
    }

    override suspend fun deleteWallpaper(wallpaperItem: WallpaperItem) {
        wallpaperDao.deleteWallpaper(mapper.mapEntityToDbModel(wallpaperItem))
    }
}
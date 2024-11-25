package ru.pvkovalev.wallpaperapp.domain.usecases

import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem
import ru.pvkovalev.wallpaperapp.domain.repository.DbRepository
import javax.inject.Inject

class AddToFavoriteWallpapersUseCase @Inject constructor(
    private val dbRepository: DbRepository
) {
    suspend fun invoke(wallpaperItem: WallpaperItem) = dbRepository.addWallpaper(wallpaperItem)
}
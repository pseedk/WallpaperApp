package ru.pvkovalev.wallpaperapp.domain.usecases

import ru.pvkovalev.wallpaperapp.domain.repository.DbRepository
import javax.inject.Inject

class GetFavoriteWallpapersUseCase @Inject constructor(
    private val dbRepository: DbRepository
) {
    fun invoke() = dbRepository.getWallpapers()
}
package ru.pvkovalev.wallpaperapp.domain.usecases

import ru.pvkovalev.wallpaperapp.data.api.repository.ApiRepositoryImpl
import javax.inject.Inject

class GetHomeWallpapersUseCase @Inject constructor(
    private val apiRepository: ApiRepositoryImpl
) {
    fun invoke() = apiRepository.getHomeWallpapers()
}
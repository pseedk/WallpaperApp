package ru.pvkovalev.wallpaperapp.domain.usecases

import ru.pvkovalev.wallpaperapp.data.api.repository.ApiRepositoryImpl
import javax.inject.Inject

class SearchWallpapersUseCase @Inject constructor(
    private val apiRepository: ApiRepositoryImpl
) {
    fun invoke(query: String) = apiRepository.searchWallpapers(query)
}
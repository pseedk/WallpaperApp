package ru.pvkovalev.wallpaperapp.presentation.fragments.download

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem
import ru.pvkovalev.wallpaperapp.domain.usecases.AddToFavoriteWallpapersUseCase
import ru.pvkovalev.wallpaperapp.domain.usecases.GetFavoriteWallpapersUseCase
import javax.inject.Inject

@HiltViewModel
class DownloadFragmentViewModel @Inject constructor(
    private val getFavoriteWallpapersUseCase: GetFavoriteWallpapersUseCase,
    private val addToFavoriteWallpapersUseCase: AddToFavoriteWallpapersUseCase
) : ViewModel() {

    val wallpapers: Flow<List<WallpaperItem>> = getFavoriteWallpapersUseCase.invoke()

    fun addWallpaper(wallpaperItem: WallpaperItem) {
        viewModelScope.launch {
            addToFavoriteWallpapersUseCase.invoke(wallpaperItem)
        }
    }

}
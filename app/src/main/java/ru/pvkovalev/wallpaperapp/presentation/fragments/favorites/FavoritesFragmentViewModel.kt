package ru.pvkovalev.wallpaperapp.presentation.fragments.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem
import ru.pvkovalev.wallpaperapp.domain.usecases.DeleteOnFavoriteWallpapersUseCase
import ru.pvkovalev.wallpaperapp.domain.usecases.GetFavoriteWallpapersUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesFragmentViewModel @Inject constructor(
    getFavoriteWallpapersUseCase: GetFavoriteWallpapersUseCase,
    private val deleteOnFavoriteWallpapersUseCase: DeleteOnFavoriteWallpapersUseCase
) : ViewModel() {

    val wallpapers: Flow<List<WallpaperItem>> = getFavoriteWallpapersUseCase.invoke()

    fun deleteWallpaper(wallpaperItem: WallpaperItem) {
        viewModelScope.launch {
            deleteOnFavoriteWallpapersUseCase.invoke(wallpaperItem)
        }
    }


}
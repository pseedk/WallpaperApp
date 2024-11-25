package ru.pvkovalev.wallpaperapp.presentation.fragments.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pvkovalev.wallpaperapp.domain.usecases.GetCategoryWallpapersUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryWallpapersUseCase: GetCategoryWallpapersUseCase
) : ViewModel() {

    fun getCategoryWallpapers(query: String) =
        getCategoryWallpapersUseCase.invoke(query).cachedIn(viewModelScope)

}
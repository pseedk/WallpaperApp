package ru.pvkovalev.wallpaperapp.presentation.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pvkovalev.wallpaperapp.domain.usecases.GetHomeWallpapersUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeWallpapersUseCase: GetHomeWallpapersUseCase
) : ViewModel() {

    val getHomeWallpapers = getHomeWallpapersUseCase.invoke().cachedIn(viewModelScope)
}
package ru.pvkovalev.wallpaperapp.presentation.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pvkovalev.wallpaperapp.domain.usecases.SearchWallpapersUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchWallpapersUseCase: SearchWallpapersUseCase
) : ViewModel() {

    fun searchWallpapers(query: String) =
        searchWallpapersUseCase.invoke(query).cachedIn(viewModelScope)

}
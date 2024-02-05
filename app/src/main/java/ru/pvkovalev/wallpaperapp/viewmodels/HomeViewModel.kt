package ru.pvkovalev.wallpaperapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import ru.pvkovalev.wallpaperapp.paging.HomePagingSource
import ru.pvkovalev.wallpaperapp.repository.MainRepository

class HomeViewModel : ViewModel() {
    private val repository = MainRepository()
    private val queryChannel = MutableStateFlow("")

    val homePage = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            HomePagingSource(repository.retrofitService())
        }
    ).flow.cachedIn(viewModelScope)
}
package ru.pvkovalev.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.pvkovalev.wallpaperapp.data.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.data.server.UnsplashApi
import ru.pvkovalev.wallpaperapp.ui.UnsplashPagingSource
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(
    private val unsplashApi: UnsplashApi
) : ViewModel() {
    val photos : Flow<PagingData<ResponseUnsplashPhoto>> = Pager(
        config = PagingConfig(
            pageSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { UnsplashPagingSource(unsplashApi) }
    ).flow
}
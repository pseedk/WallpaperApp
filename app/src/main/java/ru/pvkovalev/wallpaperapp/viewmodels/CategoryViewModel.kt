package ru.pvkovalev.wallpaperapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import ru.pvkovalev.wallpaperapp.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.paging.CategoryPagingSource
import ru.pvkovalev.wallpaperapp.repository.MainRepository

class CategoryViewModel(private val query: String) : ViewModel() {

    private val repository = MainRepository()
    private val queryChannel = MutableStateFlow("")

    val homePage = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CategoryPagingSource(repository.retrofitService(), query)
        }
    ).flow.cachedIn(viewModelScope)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: Flow<PagingData<ResponseUnsplashPhoto>> = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            Pager(PagingConfig(pageSize = 30)) {
                CategoryPagingSource(repository.retrofitService(), query)
            }.flow.cachedIn(viewModelScope)
        }

    fun searchItems(query: String) {
        queryChannel.value = query
    }
}
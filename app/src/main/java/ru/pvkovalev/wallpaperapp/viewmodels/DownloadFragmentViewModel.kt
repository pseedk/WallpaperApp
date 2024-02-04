package ru.pvkovalev.wallpaperapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.db.WallpaperDao
import ru.pvkovalev.wallpaperapp.db.WallpaperDatabase
import ru.pvkovalev.wallpaperapp.db.WallpaperEntity
import ru.pvkovalev.wallpaperapp.db.WallpaperRepository
import java.lang.IllegalArgumentException

class DownloadFragmentViewModel(private val application: Application) : ViewModel() {

    private val wallpaperRepository: WallpaperRepository

    init {
        val wallpaperDao: WallpaperDao = WallpaperDatabase.getDatabase(application).wallpaperDao()
        wallpaperRepository = WallpaperRepository(wallpaperDao)

    }

    private val getAllWallpapers: LiveData<List<WallpaperEntity>> =
        wallpaperRepository.getAllWallpapers

    fun addWallpaper(wallpaperEntity: WallpaperEntity) = viewModelScope.launch {
        wallpaperRepository.addWallpaper(wallpaperEntity)
    }
}

class DownloadFragmentViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DownloadFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DownloadFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
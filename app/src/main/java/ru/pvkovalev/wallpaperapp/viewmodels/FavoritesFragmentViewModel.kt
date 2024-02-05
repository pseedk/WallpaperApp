package ru.pvkovalev.wallpaperapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.db.WallpaperDao
import ru.pvkovalev.wallpaperapp.db.WallpaperDatabase
import ru.pvkovalev.wallpaperapp.db.WallpaperEntity
import ru.pvkovalev.wallpaperapp.db.WallpaperRepository

class FavoritesFragmentViewModel(private val application: Application) : ViewModel() {

    private val wallpaperRepository: WallpaperRepository
    val getAllWallpapers: LiveData<List<WallpaperEntity>>

    init {
        val wallpaperDao: WallpaperDao = WallpaperDatabase.getDatabase(application).wallpaperDao()
        wallpaperRepository = WallpaperRepository(wallpaperDao)
        getAllWallpapers = wallpaperRepository.getAllWallpapers
    }

    fun getAllNotes(): LiveData<List<WallpaperEntity>> =
        WallpaperDatabase.getDatabase(application).wallpaperDao().getAllWallpapers()

    fun addWallpaper(wallpaperEntity: WallpaperEntity) = viewModelScope.launch {
        wallpaperRepository.addWallpaper(wallpaperEntity)
    }
}

class FavoritesFragmentViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
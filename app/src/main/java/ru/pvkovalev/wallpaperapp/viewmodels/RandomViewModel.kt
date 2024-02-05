package ru.pvkovalev.wallpaperapp.viewmodels

import androidx.lifecycle.ViewModel
import ru.pvkovalev.wallpaperapp.repository.MainRepository

class RandomViewModel : ViewModel() {
    val repository = MainRepository()
}
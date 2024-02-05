package ru.pvkovalev.wallpaperapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CategoryViewModelFactory(private val query: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(query) as T
    }
}
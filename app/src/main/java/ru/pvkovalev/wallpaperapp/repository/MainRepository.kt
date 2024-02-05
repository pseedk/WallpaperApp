package ru.pvkovalev.wallpaperapp.repository

import ru.pvkovalev.wallpaperapp.networking.RetrofitApi
import ru.pvkovalev.wallpaperapp.networking.RetrofitServices

class MainRepository {
    fun retrofitService(): RetrofitServices = RetrofitApi.apiService
}
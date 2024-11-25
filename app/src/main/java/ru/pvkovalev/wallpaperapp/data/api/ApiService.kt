package ru.pvkovalev.wallpaperapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.pvkovalev.wallpaperapp.data.api.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.data.api.model.Search

interface ApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String = TOKEN,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 7
    ): List<ResponseUnsplashPhoto>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") searchQuery: String,
        @Query("client_id") clientId: String = TOKEN,
        @Query("page") page: Int = 1
    ): Search

    companion object {
        private const val TOKEN = "9AWo_bzu4P-UgvLU6rrxfDubVrV6sCaiJWqWNWVqyT0"
    }

}
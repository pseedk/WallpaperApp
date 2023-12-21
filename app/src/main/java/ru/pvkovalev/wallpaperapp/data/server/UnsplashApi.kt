package ru.pvkovalev.wallpaperapp.data.server

import retrofit2.http.GET
import retrofit2.http.Query
import ru.pvkovalev.wallpaperapp.data.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.utils.TOKEN

interface UnsplashApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String = TOKEN,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 7
    ): List<ResponseUnsplashPhoto>
}
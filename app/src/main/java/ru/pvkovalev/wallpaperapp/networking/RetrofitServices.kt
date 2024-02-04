package ru.pvkovalev.wallpaperapp.networking

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pvkovalev.wallpaperapp.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.model.Search
import ru.pvkovalev.wallpaperapp.utils.TOKEN

interface RetrofitServices {

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

    @GET("search/{category}")
    suspend fun getCategory(
        @Path("category") category: String,
        @Query("client_id") clientId: String = TOKEN,
        @Query("page") page: Int = 1
    ) : Search
}
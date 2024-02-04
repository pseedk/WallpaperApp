package ru.pvkovalev.wallpaperapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseUnsplashPhoto(
    val id: String,
    val urls: UnsplashPhotoUrls,
    @SerializedName("blur_hash")
    val blurHash: String
) : Parcelable

@Parcelize
data class UnsplashPhotoUrls(
    @SerializedName("small")
    val small: String,
    @SerializedName("regular")
    val medium: String,
    @SerializedName("raw")
    val large: String
) : Parcelable


data class Search(
    val results: List<ResponseUnsplashPhoto>
)

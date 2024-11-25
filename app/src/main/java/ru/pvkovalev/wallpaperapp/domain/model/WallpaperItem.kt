package ru.pvkovalev.wallpaperapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WallpaperItem(
    val id: Int = UNDEFINED_ID,
    val wallpaperUrl: String?
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}

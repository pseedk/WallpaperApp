package ru.pvkovalev.wallpaperapp.utils

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class SetWallpaperTask(
    private val url: String,
    private val wallpaperManager: WallpaperManager
) {
    suspend fun execute(): Boolean {
        var changed = false
        withContext(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                BitmapFactory.decodeStream(inputStream)?.let { bitmap ->
                    wallpaperManager.setBitmap(bitmap)
                    changed = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                changed = false
            }
        }
        return changed
    }
}
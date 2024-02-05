package ru.pvkovalev.wallpaperapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM wallpapers_table")
    fun getAllWallpapers(): LiveData<List<WallpaperEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWallpaper(wallpaper: WallpaperEntity)

}
package ru.pvkovalev.wallpaperapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.pvkovalev.wallpaperapp.data.db.model.WallpaperEntity

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM wallpapers_table")
    fun getAllWallpapers(): Flow<List<WallpaperEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWallpaper(wallpaper: WallpaperEntity)

    @Delete
    suspend fun deleteWallpaper(wallpaper: WallpaperEntity)

}
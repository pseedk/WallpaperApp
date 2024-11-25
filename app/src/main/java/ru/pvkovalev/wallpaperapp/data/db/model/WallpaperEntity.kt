package ru.pvkovalev.wallpaperapp.data.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wallpapers_table")
@Parcelize
data class WallpaperEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val wallpaperUrl: String?
) : Parcelable
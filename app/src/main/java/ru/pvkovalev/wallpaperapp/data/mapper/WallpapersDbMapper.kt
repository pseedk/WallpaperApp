package ru.pvkovalev.wallpaperapp.data.mapper

import ru.pvkovalev.wallpaperapp.data.db.model.WallpaperEntity
import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem
import javax.inject.Inject

class WallpapersDbMapper @Inject constructor()  {

    fun mapEntityToDbModel(wallpaperItem: WallpaperItem) =
        WallpaperEntity(
            id = wallpaperItem.id,
            wallpaperUrl = wallpaperItem.wallpaperUrl
        )

    private fun mapDbModelToEntity(wallpaperEntity: WallpaperEntity) =
        WallpaperItem(
            id = wallpaperEntity.id,
            wallpaperUrl = wallpaperEntity.wallpaperUrl
        )

    fun mapListDbModelToListEntity(list: List<WallpaperEntity>) =
        list.map {
            mapDbModelToEntity(it)
        }

}
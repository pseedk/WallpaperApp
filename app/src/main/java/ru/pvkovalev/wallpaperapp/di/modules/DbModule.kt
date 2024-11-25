package ru.pvkovalev.wallpaperapp.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.pvkovalev.wallpaperapp.data.db.AppDatabase
import ru.pvkovalev.wallpaperapp.data.db.WallpaperDao
import ru.pvkovalev.wallpaperapp.data.db.repository.DbRepositoryImpl
import ru.pvkovalev.wallpaperapp.data.mapper.WallpapersDbMapper
import ru.pvkovalev.wallpaperapp.domain.repository.DbRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun providesRepository(wallpaperDao: WallpaperDao, mapper: WallpapersDbMapper): DbRepository =
        DbRepositoryImpl(wallpaperDao, mapper)

    @Singleton
    @Provides
    fun providesNotesDao(
        application: Application
    ): WallpaperDao {
        return AppDatabase.getInstance(application).wallpapersDao()
    }
}
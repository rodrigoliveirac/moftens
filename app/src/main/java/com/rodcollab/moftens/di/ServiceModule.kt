package com.rodcollab.moftens.di

import com.rodcollab.moftens.core.service.song.SongService
import com.rodcollab.moftens.core.service.song.SongServiceImpl
import com.rodcollab.moftens.core.service.user.UserService
import com.rodcollab.moftens.core.service.user.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ServiceModule {

    @Singleton
    @Binds
    abstract fun providesSongService(impl: SongServiceImpl): SongService

    @Singleton
    @Binds
    abstract fun providesUserService(impl: UserServiceImpl): UserService
}
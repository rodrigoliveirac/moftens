package com.rodcollab.moftens.player.recentlyPlayedTracks.domain.di

import com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase.GetRecentlyPlayedTracksUseCase
import com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase.GetRecentlyPlayedTracksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CollectionsModule {

    @Singleton
    @Binds
    abstract fun providesGetRecentlyPlayedTracksUseCase(impl : GetRecentlyPlayedTracksUseCaseImpl) : GetRecentlyPlayedTracksUseCase

}
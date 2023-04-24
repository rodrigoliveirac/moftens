package com.rodcollab.moftens.users.topItems.track.domain.di

import com.rodcollab.moftens.users.topItems.track.domain.GetTopItemsTrackUseCase
import com.rodcollab.moftens.users.topItems.track.domain.GetTopItemsTrackUseCaseImpl
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
    abstract fun providesGetTopItemsTrackUseCase(impl: GetTopItemsTrackUseCaseImpl) : GetTopItemsTrackUseCase
}
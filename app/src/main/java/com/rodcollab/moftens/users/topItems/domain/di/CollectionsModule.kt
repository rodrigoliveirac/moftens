package com.rodcollab.moftens.users.topItems.domain.di

import com.rodcollab.moftens.users.topItems.domain.usecase.GetTopItemsArtistUseCase
import com.rodcollab.moftens.users.topItems.domain.usecase.GetTopItemsArtistUseCaseImpl
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
    abstract fun providesGetUserTopItems(impl: GetTopItemsArtistUseCaseImpl) : GetTopItemsArtistUseCase
}
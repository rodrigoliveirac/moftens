package com.rodcollab.moftens.users.topItems.domain.di

import com.rodcollab.moftens.users.topItems.domain.usecase.GetTopItemsUseCase
import com.rodcollab.moftens.users.topItems.domain.usecase.GetTopItemsUseCaseImpl
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
    abstract fun providesGetUserTopItems(impl: GetTopItemsUseCaseImpl) : GetTopItemsUseCase
}
package com.rodcollab.moftens.di

import com.rodcollab.moftens.data.service.user.UserService
import com.rodcollab.moftens.data.service.user.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UserServiceModule {

    @Singleton
    @Binds
    abstract fun providesUserService(impl: UserServiceImpl): UserService
}
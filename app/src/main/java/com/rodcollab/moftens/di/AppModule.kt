package com.rodcollab.moftens.di

import android.app.Application
import android.content.SharedPreferences
import com.rodcollab.moftens.core.util.Constants.SPOTIFY_PREFS
import com.rodcollab.moftens.core.prefs.DefaultPreferences
import com.rodcollab.moftens.core.prefs.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(app : Application) : SharedPreferences {
        return app.getSharedPreferences(SPOTIFY_PREFS, 0)
    }

    @Provides
    @Singleton
    fun providesPreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences, sharedPreferences.edit())
    }
}
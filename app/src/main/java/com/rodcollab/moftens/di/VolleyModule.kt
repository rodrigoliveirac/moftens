package com.rodcollab.moftens.di

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VolleyModule {

    @Provides
    @Singleton
    fun providesQueue(@ApplicationContext context: Context): RequestQueue {
        return Volley.newRequestQueue(context)
    }
}
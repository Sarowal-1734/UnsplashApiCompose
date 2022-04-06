package com.example.unsplashapicompose.di

import android.content.Context
import com.example.unsplashapicompose.managers.ConnectionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.unsplashapicompose.navigation.NavigationManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesNavigationManager() = NavigationManager()

    @Singleton
    @Provides
    fun provideConnectionManager(@ApplicationContext appContext: Context) =
        ConnectionManager(appContext)

}
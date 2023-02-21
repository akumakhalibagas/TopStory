package com.makhalibagas.topstory.di

import android.content.Context
import android.content.SharedPreferences
import com.makhalibagas.topstory.data.source.local.SharedPref
import com.makhalibagas.topstory.data.source.local.SharedPref.Companion.SESSION_NAME
import com.makhalibagas.topstory.data.source.remote.repository.TopStoryRepository
import com.makhalibagas.topstory.domain.usecase.TopStoryCommentUseCase
import com.makhalibagas.topstory.domain.usecase.TopStoryDetailUseCase
import com.makhalibagas.topstory.domain.usecase.TopStoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTopStoryUseCase(repository: TopStoryRepository) =
        TopStoryUseCase(repository)

    @Provides
    @Singleton
    fun provideTopStoryDetailUseCase(repository: TopStoryRepository) =
        TopStoryDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideTopStoryCommentUseCase(repository: TopStoryRepository) =
        TopStoryCommentUseCase(repository)

    @Provides
    fun providesSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE)

    @Provides
    fun providesSession(sharedPreferences: SharedPreferences) = SharedPref(sharedPreferences)

}
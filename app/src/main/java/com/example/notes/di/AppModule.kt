package com.example.notes.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class])
class AppModule(val context: Context) {
    @Provides
    fun provideContext() = context
}
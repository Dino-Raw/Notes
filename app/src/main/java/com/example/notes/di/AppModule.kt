package com.example.notes.di

import android.content.Context
import com.example.domain.model.NoteDomain
import com.example.notes.R
import com.example.notes.presentation.viewmodel.HomeViewModel
import com.example.notes.presentation.viewmodel.HomeViewModel_Factory
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class])
class AppModule(val context: Context) {
    @Provides
    fun provideContext() = context
}
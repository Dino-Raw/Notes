package com.example.notes.di

import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataBindModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository
}
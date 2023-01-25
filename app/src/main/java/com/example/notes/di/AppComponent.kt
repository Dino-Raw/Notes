package com.example.notes.di

import com.example.notes.presentation.fragment.HomeFragment
import com.example.notes.presentation.fragment.NoteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(noteFragment: NoteFragment)
}
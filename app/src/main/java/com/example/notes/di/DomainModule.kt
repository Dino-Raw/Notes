package com.example.notes.di

import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.GetNotesUseCase
import com.example.domain.usecase.InsertNoteUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import com.example.notes.R
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideDeleteNote(repository: Repository) =
        DeleteNoteUseCase(repository = repository)

    @Provides
    fun provideGetNotes(repository: Repository) =
        GetNotesUseCase(repository = repository)

    @Provides
    fun provideInsertNote(repository: Repository) =
        InsertNoteUseCase(repository = repository)

    @Provides
    fun provideUpdateNote(repository: Repository) =
        UpdateNoteUseCase(repository = repository)

    @Provides
    fun provideNoteDomain() = NoteDomain (
        id = null,
        title = "",
        description = "",
        pinned = false,
        color = R.color.note_background_0,
    )
}
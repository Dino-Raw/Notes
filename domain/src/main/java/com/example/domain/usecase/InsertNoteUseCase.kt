package com.example.domain.usecase

import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository

class InsertNoteUseCase(
    private val repository: Repository
) {
    suspend fun execute(note: NoteDomain) {
        repository.insertNote(note = note)
    }
}
package com.example.domain.usecase

import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository

class GetNotesUseCase(
    private val repository: Repository
) {
    suspend fun execute(): List<NoteDomain> {
        return repository.getNotes()
    }
}
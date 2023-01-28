package com.example.domain.usecase

import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: Repository
) {
    fun execute(pinned: Boolean = false): Flow<List<NoteDomain>> {
        return if (pinned) repository.getPinnedNotes() else repository.getNotPinnedNotes()
    }
}
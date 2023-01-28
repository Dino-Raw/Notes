package com.example.domain.repository

import com.example.domain.model.NoteDomain
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNotPinnedNotes(): Flow<List<NoteDomain>>
    fun getPinnedNotes(): Flow<List<NoteDomain>>
    suspend fun insertNote(note: NoteDomain)
    suspend fun updateNote(note: NoteDomain)
    suspend fun deleteNote(note: NoteDomain)
}
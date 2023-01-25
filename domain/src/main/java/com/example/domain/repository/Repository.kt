package com.example.domain.repository

import com.example.domain.model.NoteDomain

interface Repository {
    fun getNotes(): List<NoteDomain>
    suspend fun insertNote(note: NoteDomain)
    suspend fun updateNote(note: NoteDomain)
    suspend fun deleteNote(note: NoteDomain)
}
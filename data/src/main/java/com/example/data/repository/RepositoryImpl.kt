package com.example.data.repository

import com.example.data.model.dao.NoteDao
import com.example.data.model.entity.NoteData
import com.example.data.toData
import com.example.data.toDomain
import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): Repository {
    override fun getNotPinnedNotes(): Flow<List<NoteDomain>> {
        return noteDao.getAllNotPinned().toDomain()
    }

    override fun getPinnedNotes(): Flow<List<NoteDomain>> {
        return noteDao.getAllPinned().toDomain()
    }

    override suspend fun insertNote(note: NoteDomain) {
        noteDao.insert(note.toData())
    }

    override suspend fun updateNote(note: NoteDomain) {
        noteDao.update(note.toData())
    }

    override suspend fun deleteNote(note: NoteDomain) {
        noteDao.delete(note.toData())
    }
}
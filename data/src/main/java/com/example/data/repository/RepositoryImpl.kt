package com.example.data.repository

import com.example.data.model.dao.NoteDao
import com.example.data.model.entity.NoteData
import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val noteDao: NoteDao
): Repository {
    override fun getNotes(): List<NoteDomain> {
        return notesDataToNotesDomain(noteDao.getAll())
    }

    override suspend fun insertNote(note: NoteDomain) {
        noteDao.insert(noteDomainToNoteData(note))
    }

    override suspend fun updateNote(note: NoteDomain) {
        noteDao.update(noteDomainToNoteData(note))
    }

    override suspend fun deleteNote(note: NoteDomain) {
        noteDao.delete(noteDomainToNoteData(note))
    }

    private fun noteDomainToNoteData(note: NoteDomain): NoteData {
        return NoteData(
            id = note.id,
            title = note.title,
            description = note.description,
            color = note.color,
        )
    }

    private fun notesDataToNotesDomain(notesData: List<NoteData>): List<NoteDomain> {
        val notesDomain = mutableListOf<NoteDomain>()

        notesData.forEach { note ->
            notesDomain.add(
                NoteDomain(
                    id = note.id,
                    title = note.title,
                    description = note.description,
                    color = note.color,
                )
            )
        }

        return notesDomain
    }
}
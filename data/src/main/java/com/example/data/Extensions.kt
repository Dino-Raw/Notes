package com.example.data

import com.example.data.model.entity.NoteData
import com.example.domain.model.NoteDomain
import kotlinx.coroutines.flow.*

fun NoteDomain.toData() = NoteData (
    id = this.id,
    title = this.title,
    description = this.description,
    color = this.color,
    pinned = this.pinned,
)

fun NoteData.toDomain() = NoteDomain (
    id = this.id,
    title = this.title,
    description = this.description,
    color = this.color,
    pinned = this.pinned,
)

fun Array<out NoteDomain>.toData(): Array<out NoteData> = map { it.toData() }.toTypedArray()

fun List<NoteData>.toDomain() = map { it.toDomain() }

fun Flow<List<NoteData>>.toDomain() = map { it.toDomain() }

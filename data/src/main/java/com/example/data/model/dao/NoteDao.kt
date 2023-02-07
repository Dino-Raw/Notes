package com.example.data.model.dao

import androidx.room.*
import com.example.data.model.entity.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteData: NoteData)

    @Update
    suspend fun update(vararg noteData: NoteData)

    @Delete
    suspend fun delete(vararg noteData: NoteData)

    @Query("SELECT * FROM Note WHERE pinned = 0")
    fun getAllNotPinned(): Flow<List<NoteData>>

    @Query("SELECT * FROM Note WHERE pinned = 1")
    fun getAllPinned(): Flow<List<NoteData>>
}
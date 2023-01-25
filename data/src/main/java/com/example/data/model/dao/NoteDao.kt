package com.example.data.model.dao

import androidx.room.*
import com.example.data.model.entity.NoteData

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteData: NoteData)

    @Update
    suspend fun update(noteData: NoteData)

    @Delete
    suspend fun delete(noteData: NoteData)

    @Query("SELECT * FROM Note ORDER BY id ASC")
    fun getAll(): List<NoteData>
}
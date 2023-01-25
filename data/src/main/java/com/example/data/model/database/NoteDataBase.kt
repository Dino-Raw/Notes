package com.example.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.dao.NoteDao
import com.example.data.model.entity.NoteData

@Database(
    entities = [NoteData::class],
    version = 1,
)
abstract class NoteDataBase: RoomDatabase() {
    abstract val noteDao: NoteDao
}
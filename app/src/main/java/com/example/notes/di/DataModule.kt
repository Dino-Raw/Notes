package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.data.model.dao.NoteDao
import com.example.data.model.database.NoteDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataBindModule::class])
class DataModule {
    @Singleton
    @Provides
    fun provideDataBase(context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context,
            NoteDataBase::class.java,
            "NoteDataBase"
        ).build()
    }

    @Provides
    fun provideNoteDao(dataBase: NoteDataBase): NoteDao {
        return dataBase.noteDao
    }
}
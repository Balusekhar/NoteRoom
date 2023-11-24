package com.example.noteroom.di

import android.app.Application
import androidx.room.Room
import com.example.noteroom.room.NoteDao
import com.example.noteroom.room.NoteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {


    @Singleton
    @Provides
    fun provideDatabase(app: Application): NoteDb {
        return Room.databaseBuilder(app, NoteDb::class.java, "note_db").build()
    }

    @Singleton
    @Provides
    fun provideDao(noteDb: NoteDb): NoteDao {
        return noteDb.noteDao()
    }

}

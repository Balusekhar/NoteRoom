package com.example.noteroom.room

import androidx.lifecycle.LiveData
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteDao: NoteDao) {

    val allNotes:LiveData<List<NoteObject>> = noteDao.getAllNotes()

    suspend fun insertNote(noteObject: NoteObject) = noteDao.addNote(noteObject)
    suspend fun deleteNote(noteObject: NoteObject) = noteDao.deleteNote(noteObject)
    suspend fun updateNote(noteObject: NoteObject) = noteDao.updateNote(noteObject)
}
package com.example.noteroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.noteroom.room.NoteObject
import com.example.noteroom.room.NoteRepo
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepo: NoteRepo) : ViewModel() {

    val allNotes: LiveData<List<NoteObject>> = noteRepo.allNotes

    fun addNote(noteObject: NoteObject) = viewModelScope.launch {
        noteRepo.insertNote(noteObject)
    }

    fun deleteNote(noteObject: NoteObject) = viewModelScope.launch {
        noteRepo.deleteNote(noteObject)
    }

    fun updateNote(noteObject: NoteObject) = viewModelScope.launch {
        noteRepo.updateNote(noteObject)
    }
}

class NoteViewModelFactory(private val noteRepo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

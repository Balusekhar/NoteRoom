package com.example.noteroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteroom.NoteViewModel
import com.example.noteroom.NoteViewModelFactory
import com.example.noteroom.R
import com.example.noteroom.databinding.ActivityMainBinding
import com.example.noteroom.databinding.ActivityNoteBinding
import com.example.noteroom.room.NoteDb
import com.example.noteroom.room.NoteObject
import com.example.noteroom.room.NoteRepo

class NoteActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNoteBinding.inflate(layoutInflater)
    }
    private lateinit var noteViewModel : NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val noteRepo = NoteRepo(NoteDb.getDatabase(this).noteDao())
        val viewModelFactory = NoteViewModelFactory(noteRepo)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)


        if(intent.hasExtra("note_id")){
            val noteId = intent.getIntExtra("note_id", 0)
            val noteTitle = intent.getStringExtra("note_title")
            val noteDescription = intent.getStringExtra("note_desc")
            binding.editTextNoteTitle.setText(noteTitle)
            binding.editTextNoteDescription.setText(noteDescription)

            binding.buttonSaveNote.setOnClickListener {
                val title = binding.editTextNoteTitle.text.toString()
                val description = binding.editTextNoteDescription.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val newNote = NoteObject(id = noteId,noteTitle = title, noteDescription = description)
                    noteViewModel.updateNote(newNote)
                    finish() // Close the activity after saving
                } else {
                    Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }else{

            binding.buttonSaveNote.setOnClickListener {
                val title = binding.editTextNoteTitle.text.toString()
                val description = binding.editTextNoteDescription.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val newNote = NoteObject(noteTitle = title, noteDescription = description)
                    noteViewModel.addNote(newNote)
                    finish() // Close the activity after saving
                } else {
                    Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
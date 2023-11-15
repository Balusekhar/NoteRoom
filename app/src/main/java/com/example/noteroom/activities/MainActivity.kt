package com.example.noteroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteroom.NoteViewModel
import com.example.noteroom.NoteViewModelFactory
import com.example.noteroom.NotesAdapter
import com.example.noteroom.databinding.ActivityMainBinding
import com.example.noteroom.room.NoteDb
import com.example.noteroom.room.NoteObject
import com.example.noteroom.room.NoteRepo

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var noteList: LiveData<List<NoteObject>>
    private lateinit var noteViewModel : NoteViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val noteRepo  = NoteRepo(NoteDb.getDatabase(this).noteDao())
        val viewModelFactory = NoteViewModelFactory(noteRepo)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

//        notesAdapter = NotesAdapter(emptyList()){
//            intent.putExtra("note_id", it.id) // Assuming each note has a unique ID
//            intent.putExtra("note_title", it.noteTitle) // Assuming each note has a unique ID
//            intent.putExtra("note_desc", it.noteDescription) // Assuming each note has a unique ID
//            startActivity(Intent(this,NoteActivity::class.java))
//        }

        notesAdapter = NotesAdapter(emptyList(),
            { note ->
                // Handling note click
                val intent = Intent(this, NoteActivity::class.java)
                intent.putExtra("note_id", note.id)
                intent.putExtra("note_title", note.noteTitle)
                intent.putExtra("note_desc", note.noteDescription)
                startActivity(intent)
            },
            { noteToDelete ->
                // Handling note delete
                noteViewModel.deleteNote(noteToDelete)
            }
        )


        binding.mainRv.adapter = notesAdapter
        binding.mainRv.layoutManager = LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this,Observer{
            notesAdapter.updateNotes(it)
        })


        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this@MainActivity, NoteActivity::class.java))
        }
    }
}
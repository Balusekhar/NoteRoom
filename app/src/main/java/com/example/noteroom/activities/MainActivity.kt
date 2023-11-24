package com.example.noteroom.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteroom.NoteViewModel
import com.example.noteroom.NotesAdapter
import com.example.noteroom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Initialize ViewModel using by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        noteViewModel.allNotes.observe(this, Observer {
            notesAdapter.updateNotes(it)
        })


        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this@MainActivity, NoteActivity::class.java))
        }
    }
}
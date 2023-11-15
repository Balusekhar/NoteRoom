package com.example.noteroom


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteroom.databinding.RvItemBinding
import com.example.noteroom.room.NoteObject

class NotesAdapter(private var notes: List<NoteObject>,
                   private val onItemClick: (NoteObject) -> Unit,
                   private val onItemDelete: (NoteObject) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // ViewHolder class with View Binding
    class NoteViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

    // onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // onBindViewHolder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNoteObject = notes[position]
        holder.binding.tvNoteTitle.text = currentNoteObject.noteTitle
        holder.binding.tvNoteDescription.text = currentNoteObject.noteDescription

        holder.itemView.setOnClickListener {
            onItemClick(currentNoteObject)
        }

        holder.binding.deleteButton.setOnClickListener {
            onItemDelete(currentNoteObject)
        }
    }

    // getItemCount
    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<NoteObject>) {
        notes = newNotes
        notifyDataSetChanged() // This tells the RecyclerView to refresh
    }


}

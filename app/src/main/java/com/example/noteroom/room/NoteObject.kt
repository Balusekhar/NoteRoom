package com.example.noteroom.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteObject (

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    var noteTitle:String,

    var noteDescription:String
)
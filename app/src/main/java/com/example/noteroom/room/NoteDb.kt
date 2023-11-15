package com.example.noteroom.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteObject::class], version = 1, exportSchema = false)
abstract class NoteDb : RoomDatabase() {

    abstract fun noteDao(): NoteDao


    companion object {

        @Volatile
        private var INSTANCE: NoteDb? = null

        fun getDatabase(context: Context): NoteDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDb::class.java,
                    "note_db"
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }
}
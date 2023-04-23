package com.gmail.at.kosminaivan.notebook.model.repository

import android.content.Context
import com.gmail.at.kosminaivan.notebook.model.Note
import java.sql.Timestamp

class JsonRepository (context: Context) : Repository {

    var jsonString: String = ""

    override fun addNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun loadNotesForTimeRange(start: Timestamp, end: Timestamp): List<Note> {
        TODO("Not yet implemented")
    }

    override fun loadNoteById(id: Long): Note {
        TODO("Not yet implemented")
    }
}
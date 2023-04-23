package com.gmail.at.kosminaivan.notebook.model.repository

import com.gmail.at.kosminaivan.notebook.model.Note
import java.sql.Timestamp

interface Repository {
    fun addNote(note: Note)

    fun loadNotesForTimeRange(start: Timestamp, end: Timestamp): List<Note>

    fun loadNoteById(id: Long) : Note
}
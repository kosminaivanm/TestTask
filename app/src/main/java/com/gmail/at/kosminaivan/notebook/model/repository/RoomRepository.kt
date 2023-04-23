package com.gmail.at.kosminaivan.notebook.model.repository

import android.content.Context
import androidx.room.Room
import com.gmail.at.kosminaivan.notebook.model.Note
import com.gmail.at.kosminaivan.notebook.model.repository.room.AppDatabase
import com.gmail.at.kosminaivan.notebook.model.repository.room.NoteEntity
import java.sql.Timestamp

class RoomRepository(context: Context): Repository {

    val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).apply {
        fallbackToDestructiveMigration()
        allowMainThreadQueries()
    }.build()

    val noteDao = db.noteDao()

    override fun addNote(note: Note) {
        db.noteDao().insertAll(NoteEntity(
            0,
            note.dateStart.time,
            note.dateFinish.time,
            note.title,
            note.description
        ))
    }



    override fun loadNotesForTimeRange(start: Timestamp, end: Timestamp): List<Note> {
        return noteDao.loadAllNotesForRange(start.time, end.time).map {
            getNoteFromNoteEntity(it)
        }
    }


    override fun loadNoteById(id: Long): Note = getNoteFromNoteEntity(db.noteDao().loadSingle(id))

    companion object {
        fun getNoteFromNoteEntity(noteEntity: NoteEntity) = Note(
            noteEntity.id.toLong(),
            Timestamp(noteEntity.dateStart),
            Timestamp(noteEntity.dateFinish),
            noteEntity.name,
            noteEntity.description
        )
    }
}


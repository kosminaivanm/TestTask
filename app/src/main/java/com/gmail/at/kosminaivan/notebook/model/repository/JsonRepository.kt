package com.gmail.at.kosminaivan.notebook.model.repository

import android.content.Context
import com.gmail.at.kosminaivan.notebook.R
import com.gmail.at.kosminaivan.notebook.model.Note
import com.gmail.at.kosminaivan.notebook.model.repository.json.NoteJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.sql.Timestamp


class JsonRepository(val context: Context) : Repository {

    private val gson = Gson()
    private var jsonString: String = ""

    private var notesList: MutableList<Note> = mutableListOf()


    override fun addNote(note: Note) {
        notesList.add(
            Note(
                notesList.size.toLong(),
                note.dateStart,
                note.dateFinish,
                note.title,
                note.description
            )
        )
        saveJson()
    }

    override fun loadNotesForTimeRange(start: Timestamp, end: Timestamp): List<Note> {
        readJson()
        return notesList.filter { note: Note ->
            (note.dateStart > start && note.dateStart < end)
                    || (note.dateFinish > start && note.dateFinish < end)
                    || (note.dateStart < start && note.dateFinish > end)
        }
    }

    override fun loadNoteById(id: Long): Note {
        readJson()

        return notesList.first { note: Note -> note.id == id }
    }

    private fun saveJson() {
        jsonString = gson.toJson(notesList.map { getJsonNoteFromNote(it) })
        val file = File(context.filesDir, FILE_NAME)
        FileOutputStream(file).use {
            val bytes = jsonString.toByteArray()
            it.write(bytes)
        }
    }

    private fun readJson() {
        try {
            readJsonNotesLocalFile()
        } catch (_: Exception) {
            readJsonNotesExampleFile()
        }
        val noteListType: Type = object : TypeToken<List<NoteJson>>() {}.type
        notesList = gson.fromJson<List<NoteJson>>(jsonString, noteListType).map {
            getNoteFromJsonNote(it)
        }.toMutableList()
    }

    private fun readJsonNotesLocalFile() {
        val file = File(context.filesDir, FILE_NAME)
        val inputStream = FileInputStream(file)
        val reader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(reader)
        val data = bufferedReader.use {
            it.readLines().joinToString(separator = "\n")
        }
        jsonString = data
    }

    private fun readJsonNotesExampleFile() {
        jsonString = context.resources.openRawResource(NOTES_EXAMPLE_FILE)
            .bufferedReader().use { it.readText() }
    }

    companion object {
        const val NOTES_EXAMPLE_FILE = R.raw.notes
        const val FILE_NAME = "JsonNotes"

        fun getNoteFromJsonNote(noteJson: NoteJson) = Note(
            noteJson.id,
            Timestamp(noteJson.dateStart.toLong()),
            Timestamp(noteJson.dateFinish.toLong()),
            noteJson.title,
            noteJson.description
        )

        fun getJsonNoteFromNote(note: Note) = NoteJson(
            note.id,
            note.dateStart.time.toString(),
            note.dateFinish.time.toString(),
            note.title,
            note.description
        )
    }
}
package com.gmail.at.kosminaivan.notebook.model

import com.gmail.at.kosminaivan.notebook.room.NoteEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Note (
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("date_start")
    @Expose
    var dateStart: Timestamp,

    @SerializedName("date_finish")
    @Expose
    var dateFinish: Timestamp,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String
){
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
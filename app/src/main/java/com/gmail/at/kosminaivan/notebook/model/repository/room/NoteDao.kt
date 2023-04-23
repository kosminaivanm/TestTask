package com.gmail.at.kosminaivan.notebook.model.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun insertAll(vararg noteEntity: NoteEntity)

    @Query("SELECT * FROM noteEntity WHERE (date_start BETWEEN :start AND :end) OR (date_finish BETWEEN :start AND :end) OR (date_start < :start AND date_finish > :end)")
    fun loadAllNotesForRange(start: Long, end: Long): Array<NoteEntity>

    @Query("DELETE FROM noteEntity")
    fun nukeTable()

    @Query("SELECT * FROM noteEntity WHERE id=:id")
    fun loadSingle(id: Long) : NoteEntity

}
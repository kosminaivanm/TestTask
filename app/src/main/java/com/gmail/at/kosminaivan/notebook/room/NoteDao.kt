package com.gmail.at.kosminaivan.notebook.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.sql.Timestamp

@Dao
interface NoteDao {

    @Insert
    fun insertAll(vararg noteEntity: NoteEntity)

    @Query("SELECT * FROM noteEntity WHERE :date BETWEEN date_start AND date_finish")
    fun loadAllNotesDateIncludes(date: Long): Array<NoteEntity>

    @Query("DELETE FROM noteEntity")
    fun nukeTable()

    @Query("SELECT * FROM noteEntity WHERE id=:id")
    fun loadSingle(id: Long) : NoteEntity

}
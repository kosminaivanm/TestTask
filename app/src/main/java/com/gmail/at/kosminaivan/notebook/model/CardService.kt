package com.gmail.at.kosminaivan.notebook.model

import android.content.Context
import androidx.room.Room
import com.gmail.at.kosminaivan.notebook.App
import com.gmail.at.kosminaivan.notebook.room.AppDatabase
import com.gmail.at.kosminaivan.notebook.room.NoteDao
import com.gmail.at.kosminaivan.notebook.room.NoteEntity
import java.sql.Timestamp

typealias CardListener = (cards: List<Card>) -> Unit

class Card(
    val time: String,
    var notes: List<Note>
)

class CardService(val db: AppDatabase, val applicationContext: Context) {

    private var cards: List<Card> = (0..23).map { Card("$it:00", emptyList()) }

    private val noteDao = db.noteDao()

    var listeners = mutableSetOf<CardListener>()

    var currentCalendarTimestamp: Timestamp = Timestamp(System.currentTimeMillis())
        set(value)
        {
            field = value
            cards = getCardsForDate(value)
            notifyChanges()
        }

    fun getCardsForDate(date: Timestamp): List<Card> {
        cards.forEachIndexed { index ,value ->
            value.notes =
                noteDao.loadAllNotesDateIncludes(date.time + index * hourInMills).map{
                    Note.getNoteFromNoteEntity(it)
                }
        }
        return cards
    }

    fun getNoteById(id: Long) : Note
    {
        return Note.getNoteFromNoteEntity(db.noteDao().loadSingle(id))
    }
    fun createNoteForTimeStampRange(dateStart: Timestamp, dateEnd: Timestamp, name: String, description: String)
    {
        db.noteDao().insertAll(NoteEntity(
            0,
            dateStart.time + 1,
            dateEnd.time,
            name,
            description
        ))
        updateCards()
    }

    private fun updateCards()
    {
        cards = getCardsForDate(currentCalendarTimestamp)
        notifyChanges()
    }

    fun addListener(listener: CardListener) {
        listeners.add(listener)
        listener.invoke(cards)
    }

    fun removeListener(listener: CardListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(cards) }
    }


    companion object {
        const val dayInMillis = 86400000L
        const val hourInMills = (dayInMillis / 24)
        const val taskDefaultDuration = (hourInMills * 1.5).toLong()
    }

}
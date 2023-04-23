package com.gmail.at.kosminaivan.notebook.model

import com.gmail.at.kosminaivan.notebook.model.repository.Repository
import java.sql.Timestamp

typealias CardListener = (cards: List<Card>) -> Unit

class CardService(private val rep: Repository) {

    private var cards: List<Card> = (0..23).map { Card("$it:00", emptyList()) }

    var listeners = mutableSetOf<CardListener>()

    var currentCalendarTimestamp: Timestamp = Timestamp(System.currentTimeMillis())
        set(value) {
            field = value
            cards = getCardsForDate(value)
            notifyChanges()
        }

    fun getCardsForDate(date: Timestamp): List<Card> {
        cards.forEachIndexed { index, value ->
            val notes =
                rep.loadNotesForTimeRange(
                    Timestamp(date.time + index * hourInMills + 1),
                    Timestamp(date.time + (index + 1) * hourInMills - 1)
                ).toMutableList()
            value.notes = notes.distinct()
        }
        return cards
    }

    fun getNoteById(id: Long): Note {
        return rep.loadNoteById(id)
    }

    fun createNoteForTimeStampRange(
        dateStart: Timestamp,
        dateEnd: Timestamp,
        name: String,
        description: String
    ) {
        rep.addNote(
            Note(
                0,
                Timestamp(dateStart.time),
                Timestamp(dateEnd.time),
                name,
                description
            )
        )
        updateCards()
    }

    private fun updateCards() {
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
    }

}
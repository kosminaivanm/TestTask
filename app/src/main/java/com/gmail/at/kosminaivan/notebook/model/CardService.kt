package com.gmail.at.kosminaivan.notebook.model

typealias CardListener = (cards: List<Card>) -> Unit

class CardService {
    private var notes = mutableListOf<Note>()
    private var cards: List<Card> = (0..23)
        .map {
            Card(
                "$it:00", listOf(
                    Note(it.toLong(), "$it:00", "${it+1}:00", "Задача в $it:00", "Описание"),
                    Note(it.toLong(), "${it + 2}:00", "${it + 2}:00", "Вторая задача в $it:00", "Описание второе")
                )
            )
        }.toMutableList()

    private val listeners = mutableSetOf<CardListener>()
    private val noteService = NoteService()

    fun getCards(): List<Card> {
        return cards
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
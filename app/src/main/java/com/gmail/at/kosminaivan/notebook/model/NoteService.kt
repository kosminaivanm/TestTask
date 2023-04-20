package com.gmail.at.kosminaivan.notebook.model

typealias NoteListener = (users: List<Note>) -> Unit

class NoteService {
    private var notes = mutableListOf<Note>()

    private val listeners = mutableSetOf<CardListener>()

    init {
        val startDate = (System.currentTimeMillis() / 1000).toString()
        notes = (1..100)
            .map {
                Note(
                    1,
                    " ${startDate + it * 2}",
                    "${startDate + hourInMills / 1000}",
                    "MyTask $it",
                    "Описание $it"
                )
            }
            .toMutableList()
    }


    companion object {
        const val dayInMillis = 86400000L
        const val hourInMills = (dayInMillis / 24)
        const val taskDefaultDuration = (hourInMills * 1.5).toLong()

    }
}
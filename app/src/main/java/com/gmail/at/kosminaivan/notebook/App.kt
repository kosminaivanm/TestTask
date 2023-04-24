package com.gmail.at.kosminaivan.notebook

import android.app.Application
import com.gmail.at.kosminaivan.notebook.model.CardService
import com.gmail.at.kosminaivan.notebook.model.repository.JsonRepository
import com.gmail.at.kosminaivan.notebook.model.repository.RoomRepository
import com.google.android.material.color.DynamicColors


class App : Application() {
    lateinit var cardService: CardService

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        cardService = if (useJson)
            CardService(JsonRepository(applicationContext))
        else
            CardService(RoomRepository(applicationContext))
    }

    companion object {
        const val useJson: Boolean = false
    }
}

package com.gmail.at.kosminaivan.notebook

import android.app.Application
import com.gmail.at.kosminaivan.notebook.model.CardService
import com.gmail.at.kosminaivan.notebook.model.repository.RoomRepository


class App : Application() {
       lateinit var cardService: CardService

       override fun onCreate() {
              super.onCreate()
              cardService = CardService(RoomRepository(applicationContext))
       }
}

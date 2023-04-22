package com.gmail.at.kosminaivan.notebook

import android.app.Application
import androidx.room.Room
import com.gmail.at.kosminaivan.notebook.model.CardService
import com.gmail.at.kosminaivan.notebook.room.AppDatabase


class App : Application() {
       lateinit var cardService: CardService

       override fun onCreate() {
              super.onCreate()

              val db = Room.databaseBuilder(
                     applicationContext,
                     AppDatabase::class.java, "database-name"
              ).apply {
                     fallbackToDestructiveMigration()
                     allowMainThreadQueries()
              }.build()

              cardService = CardService(db, applicationContext)
       }
}

package com.gmail.at.kosminaivan.notebook.app

import android.app.Application
import com.gmail.at.kosminaivan.notebook.model.CardService


class App : Application() {
    val cardService = CardService()
}

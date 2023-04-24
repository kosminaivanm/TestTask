package com.gmail.at.kosminaivan.notebook.model.repository.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteJson (
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("date_start")
    @Expose
    var dateStart: String,

    @SerializedName("date_finish")
    @Expose
    var dateFinish: String,

    @SerializedName("name")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String
)
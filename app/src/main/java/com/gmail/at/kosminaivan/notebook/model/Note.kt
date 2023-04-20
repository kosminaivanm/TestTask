package com.gmail.at.kosminaivan.notebook.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Note (
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("date_start")
    @Expose
    var dateStart: String,

    @SerializedName("date_finish")
    @Expose
    var dateFinish: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String
)
package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class RaceInfoResponse(
    @SerializedName("race_id")
    val raceId: String,

    @SerializedName("name_race")
    val nameRace: String,

    @SerializedName("discipline")
    val discipline: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("place_race")
    val placeRace: String
)
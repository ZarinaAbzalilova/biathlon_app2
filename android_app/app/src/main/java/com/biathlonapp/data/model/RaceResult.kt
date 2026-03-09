package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class RaceResult(
    @SerializedName("race_id")
    val raceId: String?,

    @SerializedName("race_info")
    val raceInfo: RaceInfo? = null,

    @SerializedName("athlete_performance")
    val athletePerformance: AthletePerformance? = null,

    @SerializedName("pdf_url")
    val pdfUrl: String? = null
)

data class RaceInfo(
    @SerializedName("discipline")
    val discipline: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("name_race")
    val nameRace: String? = null,

    @SerializedName("place_race")
    val placeRace: String? = null,

    @SerializedName("status")
    val status: String? = null
)

data class AthletePerformance(
    @SerializedName("start_number")
    val startNumber: Int? = null,

    @SerializedName("finish_place")
    val finishPlace: Int? = null,

    @SerializedName("miss_count")
    val missCount: Int? = null
)
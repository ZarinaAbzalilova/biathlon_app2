package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class AthleteResultsResponse(
    @SerializedName("athlete")
    val athlete: Athlete,

    @SerializedName("results")  // ← ИСПРАВЛЕНО: должно быть "results", а не "races"
    val races: List<RaceResult>? = null,  // ← название переменной может остаться races

    @SerializedName("results_count")
    val resultsCount: Int,

    @SerializedName("message")
    val message: String
)


package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class AthleteResultsResponse(
    @SerializedName("athlete")
    val athlete: Athlete,  // ← теперь Athlete содержит все поля

    @SerializedName("results")
    val results: List<RaceResult>,

    @SerializedName("results_count")
    val resultsCount: Int,

    @SerializedName("message")
    val message: String
)
package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class RaceResultsResponse(
    @SerializedName("race_info")
    val raceInfo: RaceInfoFull,

    @SerializedName("results")
    val results: List<RaceResultItem>,

    @SerializedName("results_count")
    val resultsCount: Int
)

data class RaceInfoFull(
    @SerializedName("race_id")
    val raceId: String,

    @SerializedName("name_race")
    val nameRace: String,

    @SerializedName("discipline")
    val discipline: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("place_race")
    val placeRace: String,

    @SerializedName("pdf_urls")
    val pdfUrls: List<PdfUrlInfo>? = null,
    val gender: String? = null
)

data class PdfUrlInfo(
    @SerializedName("pdf_url")
    val pdfUrl: String,

    @SerializedName("gender")
    val gender: String
)

data class RaceResultItem(
    @SerializedName("start_number")
    val startNumber: Int?,

    @SerializedName("finish_place")
    val finishPlace: Int?,

    @SerializedName("miss_count")
    val missCount: Int?,

    @SerializedName("finish_time")
    val finishTime: String?,

    @SerializedName("athlete_id")
    val athleteId: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("region")
    val region: String?,

    @SerializedName("sports_rank")
    val sportsRank: String?,

    @SerializedName("athlete_gender")
    val athleteGender: String?
)
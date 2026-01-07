package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class RaceResult(
    @SerializedName("race_id")
    val raceId: String?,

    @SerializedName("discipline")
    val discipline: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("start_number")
    val startNumber: Int? = null,

    @SerializedName("finish_place")
    val finishPlace: Int? = null,

    @SerializedName("miss_count")
    val missCount: Int? = null,

    @SerializedName("race_status")
    val raceStatus: String? = null,

    @SerializedName("pdf_url")  // ← ВАЖНО: должно совпадать с JSON
    val pdfUrl: String?
)

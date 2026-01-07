package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class PdfUrlResponse(
    @SerializedName("race_id")
    val raceId: String,

    @SerializedName("pdf_url")
    val pdfUrl: String?,

    @SerializedName("found")
    val found: Boolean
)
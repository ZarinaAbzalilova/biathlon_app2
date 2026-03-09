package com.biathlonapp.data.model

import com.google.gson.annotations.SerializedName

data class Athlete(
    @SerializedName("athlete_id")
    val athleteId: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("region_code")
    val regionCode: String? = null,

    @SerializedName("sports_rank")
    val sportsRank: String? = null,

    @SerializedName("birth_date")
    val birthDate: String? = null
)
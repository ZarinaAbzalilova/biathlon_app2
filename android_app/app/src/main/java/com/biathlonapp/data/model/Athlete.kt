package com.biathlonapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Athlete(
    @SerializedName("athlete_id")
    val athleteId: String,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("patronymic")
    val patronymic: String? = null,

    @SerializedName("birth_date")
    val birthDate: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("sports_rank")
    val sportsRank: String? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("region_code")
    val regionCode: String? = null
) : Parcelable {
    val fullName: String
        get() {
            val parts = listOfNotNull(lastName, firstName, patronymic?.takeIf { it.isNotBlank() })
            return if (parts.isNotEmpty()) {
                parts.joinToString(" ")
            } else {
                "Имя не указано"
            }
        }

    val displayGender: String
        get() = when (gender?.lowercase()) {
            "м", "male", "мужской" -> "Мужской"
            "ж", "female", "женский" -> "Женский"
            else -> gender ?: "Не указан"
        }
}

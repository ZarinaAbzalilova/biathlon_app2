package com.biathlonapp.data.model

import com.biathlonapp.data.local.FavoriteAthlete
import com.biathlonapp.data.local.FavoriteResult
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
) {
    // Конвертер в FavoriteAthlete
    fun toFavoriteAthlete(): FavoriteAthlete {
        return FavoriteAthlete(
            athleteId = this.athleteId ?: "",
            name = this.firstName ?: "",
            surname = this.lastName ?: "",
            gender = this.gender ?: "",
            sportsRank = this.sportsRank ?: "",
            photoUrl = null,
            birthDate = this.birthDate,
            region = this.region,
            coach = null,
            biography = null,
            lastName = this.lastName,
            firstName = this.firstName,
            regionCode = this.regionCode,
            lastUpdated = System.currentTimeMillis(),
            isFavorite = true,
            dateAdded = System.currentTimeMillis()
        )
    }

    // Для удобства отображения полного имени
    fun getFullName(): String {
        return "${lastName ?: ""} ${firstName ?: ""}".trim()
    }
}


// ЕДИНАЯ ВЕРСИЯ RaceResult
data class RaceResult(
    @SerializedName("race_id")
    val raceId: String? = null,

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
) {
    // Конвертер в FavoriteResult
    fun toFavoriteResult(athleteId: String, race: RaceResult): FavoriteResult {
        return FavoriteResult(
            athleteId = athleteId,
            discipline = race.raceInfo?.discipline,
            date = race.raceInfo?.date,
            nameRace = race.raceInfo?.nameRace,
            placeRace = race.raceInfo?.placeRace,
            startNumber = this.startNumber,
            finishPlace = this.finishPlace,
            missCount = this.missCount
        )
    }
}
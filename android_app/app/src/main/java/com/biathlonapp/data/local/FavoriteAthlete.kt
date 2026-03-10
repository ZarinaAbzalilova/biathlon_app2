package com.biathlonapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.biathlonapp.data.model.Athlete
import java.io.Serializable

@Entity(tableName = "favorite_athletes")
data class FavoriteAthlete(
    @PrimaryKey
    val athleteId: String,
    val name: String,           // firstName
    val surname: String,        // lastName
    val gender: String,
    val sportsRank: String,
    val photoUrl: String? = null,
    val birthDate: String?,
    val region: String?,
    val coach: String? = null,
    val biography: String? = null,
    // Добавляем недостающие поля
    val lastName: String? = null,      // для кэша
    val firstName: String? = null,     // для кэша
    val regionCode: String? = null,    // для кэша
    val lastUpdated: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = true,
    val dateAdded: Long = System.currentTimeMillis()
) : Serializable {

    // Для отображения полного имени
    fun getFullName(): String {
        return "$surname $name".trim()
    }

    // Конвертер обратно в Athlete
    fun toAthlete(): Athlete {
        return Athlete(
            athleteId = this.athleteId,
            lastName = this.lastName ?: this.surname,
            firstName = this.firstName ?: this.name,
            gender = this.gender,
            region = this.region,
            regionCode = this.regionCode,
            sportsRank = this.sportsRank,
            birthDate = this.birthDate
        )
    }
}
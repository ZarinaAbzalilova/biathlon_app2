package com.biathlonapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "favorite_results",
    foreignKeys = [
        ForeignKey(
            entity = FavoriteAthlete::class,
            parentColumns = ["athleteId"],
            childColumns = ["athleteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["athleteId"])]
)
data class FavoriteResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val athleteId: String,           // Связь с таблицей спортсменов
    val discipline: String?,
    val date: String?,
    val nameRace: String?,
    val placeRace: String?,
    val startNumber: Int?,
    val finishPlace: Int?,
    val missCount: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
) : Serializable
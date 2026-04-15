package com.biathlonapp.ui.stats

// Единый класс для отображения в адаптере
data class RaceResultDisplay(
    val discipline: String,
    val date: String,
    val nameRace: String,
    val placeRace: String,
    val startNumber: Int?,
    val finishPlace: Int?,
    val missCount: Int?,
    val pdfUrl: String? = null,  // ← ДОБАВЬ ЭТО ПОЛЕ
    val raceId: String? = null,
    val athleteGender: String? = null
)
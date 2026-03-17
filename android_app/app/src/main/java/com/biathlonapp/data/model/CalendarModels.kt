package com.biathlonapp.data.model

import java.io.Serializable
import java.util.*

data class CalendarMonth(
    val year: Int,
    val month: Int, // 0-11 (январь = 0)
    val days: List<CalendarDay>
): Serializable

data class CalendarDay(
    val date: Date,
    val dayOfMonth: Int,
    val isCurrentMonth: Boolean,
    val hasEvent: Boolean = false,
    val events: List<RaceEvent> = emptyList(),
    val hasMaleEvent: Boolean = false,  // ← НОВОЕ
    val hasFemaleEvent: Boolean = false  // ← НОВОЕ
): Serializable

// ИСПРАВЛЕНО: под данные из таблицы races
data class RaceEvent(
    val id: String, // race_id
    val raceId: String, // оригинальный race_id
    val title: String, // name_race
    val date: Date,
    val location: String, // place_race
    val discipline: String, // discipline
    val gender: String? = null, // "М" или "Ж"
    val pdfUrl: String? = null, // ссылка на протокол
    val category: String = "",
    val description: String? = null,
    val hasMale: Boolean = false,
    val hasFemale: Boolean = false
) : Serializable
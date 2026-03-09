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
    val events: List<RaceEvent> = emptyList()
): Serializable

// ИСПРАВЛЕНО: под данные из таблицы races
data class RaceEvent(
    val id: String, // race_id
    val title: String, // name_race
    val date: Date,
    val location: String, // place_race
    val discipline: String, // discipline
    val gender: String? = null, // можно добавить позже из race_pdf_urls
    val category: String = "", // можно извлечь из name_race
    val description: String? = null
) : Serializable
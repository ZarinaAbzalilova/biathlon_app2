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
    val isCurrentMonth: Boolean, // для дней из предыдущего/следующего месяца
    val hasEvent: Boolean = false, // есть ли гонка в этот день
    val events: List<RaceEvent> = emptyList()
): Serializable

data class RaceEvent(
    val id: String,
    val title: String,
    val date: Date,
    val location: String,
    val discipline: String, // "Спринт", "Индивидуальная", "Эстафета" и т.д.
    val gender: String, // "male", "female", "mixed"
    val category: String, // "Кубок мира", "Чемпионат мира", "Кубок IBU" и т.д.
    val description: String? = null
) : Serializable
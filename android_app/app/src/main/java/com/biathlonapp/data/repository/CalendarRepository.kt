package com.biathlonapp.data.repository

import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.api.DayEventsResponse
import com.biathlonapp.data.model.RaceEvent
import com.biathlonapp.utils.Result
import java.text.SimpleDateFormat
import java.util.*
import com.biathlonapp.utils.ErrorHandler

class CalendarRepository(
    private val apiService: BiathlonApiService
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend fun getRacesForMonth(year: Int, month: Int): Result<List<RaceEvent>> {
        return try {
            // month в API ожидается 1-12, в Calendar 0-11
            val response = apiService.getCalendarRaces(year, month + 1)
            if (response.isSuccessful) {
                val dayEventsList = response.body() ?: emptyList()

                // Преобразуем DayEventsResponse в список RaceEvent
                val allEvents = dayEventsList.flatMap { dayEvents ->
                    dayEvents.races.map { race ->
                        RaceEvent(
                            id = "${race.id}_${race.gender}",
                            raceId = race.id,
                            title = race.title,
                            date = dateFormat.parse(dayEvents.date) ?: Date(),
                            location = race.location,
                            discipline = race.discipline,
                            gender = race.gender,
                            pdfUrl = race.pdf_url,
                            hasMale = dayEvents.has_male == 1,
                            hasFemale = dayEvents.has_female == 1,
                            hasMixed = dayEvents.has_mixed == 1,  // ← НОВОЕ
                            isMixed = race.is_mixed  // ← НОВОЕ
                        )
                    }
                }
                Result.Success(allEvents)
            } else {
                Result.Error(Exception("Ошибка загрузки: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }

    suspend fun getRacesForDate(date: Date): Result<List<RaceEvent>> {
        return try {
            val dateStr = dateFormat.format(date)
            val response = apiService.getRacesByDate(dateStr)
            if (response.isSuccessful) {
                val races = response.body() ?: emptyList()
                Result.Success(races.map { race ->
                    RaceEvent(
                        id = "${race.id}_${race.gender}",
                        raceId = race.id,
                        title = race.title,
                        date = dateFormat.parse(race.date) ?: Date(),
                        location = race.location,
                        discipline = race.discipline,
                        gender = race.gender,
                        pdfUrl = race.pdf_url,
                        hasMale = race.gender == "М",
                        hasFemale = race.gender == "Ж"
                    )
                })
            } else {
                Result.Error(Exception("Ошибка загрузки: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
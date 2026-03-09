package com.biathlonapp.data.repository

import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.model.RaceEvent
import com.biathlonapp.utils.Result
import java.text.SimpleDateFormat
import java.util.*

class CalendarRepository(
    private val apiService: BiathlonApiService  // ← Используем BiathlonApiService
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend fun getRacesForMonth(year: Int, month: Int): Result<List<RaceEvent>> {
        return try {
            // month в API ожидается 1-12, в Calendar 0-11
            val response = apiService.getRacesByMonth(year, month + 1)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(Exception("Ошибка загрузки: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getRacesForDate(date: Date): Result<List<RaceEvent>> {
        return try {
            val dateStr = dateFormat.format(date)
            val response = apiService.getRacesByDate(dateStr)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(Exception("Ошибка загрузки: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
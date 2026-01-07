package com.biathlonapp.data.repository

import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.model.AthleteResultsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BiathlonRepository {

    private val apiService = ApiClient.apiService

    suspend fun getAthletes(): Result<List<Athlete>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAthletes()
            if (response.isSuccessful) {
                response.body()?.let { athletes ->
                    Result.success(athletes)
                } ?: Result.failure(Exception("Пустой ответ от сервера"))
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchAthletes(query: String): Result<List<Athlete>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchAthletes(query)
            if (response.isSuccessful) {
                response.body()?.let { athletes ->
                    Result.success(athletes)
                } ?: Result.failure(Exception("Пустой ответ от сервера"))
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAthleteResults(athleteId: String): Result<AthleteResultsResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAthleteResults(athleteId)
            if (response.isSuccessful) {
                response.body()?.let { results ->
                    Result.success(results)
                } ?: Result.failure(Exception("Пустой ответ от сервера"))
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // В BiathlonRepository
    suspend fun getRacePdfUrl(raceId: String, athleteId: String): String? {
        return try {
            val response = apiService.getRacePdfUrl(raceId, athleteId)
            if (response.isSuccessful && response.body()?.found == true) {
                response.body()?.pdfUrl
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}

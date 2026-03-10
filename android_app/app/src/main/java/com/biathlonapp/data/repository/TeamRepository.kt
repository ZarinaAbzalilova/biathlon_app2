package com.biathlonapp.data.repository

import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.model.TeamType
import com.biathlonapp.utils.Result

class TeamRepository {

    private val apiService = ApiClient.apiService

    suspend fun getTeamAthletes(teamType: TeamType): Result<List<Athlete>> {
        return try {
            val teamValue = when (teamType) {
                TeamType.MEN_MAIN -> "men_main"
                TeamType.WOMEN_MAIN -> "women_main"
                TeamType.MEN_RESERVE -> "men_reserve"
                TeamType.WOMEN_RESERVE -> "women_reserve"
            }

            val response = apiService.getAthletesByTeam(teamValue)  // ← здесь параметр team
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
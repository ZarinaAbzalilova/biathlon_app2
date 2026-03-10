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

            // TODO: добавить endpoint на сервере
            // val response = apiService.getAthletesByTeam(teamValue)

            // Пока заглушка - вернем пустой список
            Result.Success(emptyList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
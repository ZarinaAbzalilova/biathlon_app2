package com.biathlonapp.data.repository

import android.content.Context
import com.biathlonapp.data.local.FavoriteAthlete
import com.biathlonapp.data.local.FavoriteDatabase
import com.biathlonapp.data.local.FavoriteResult
import com.biathlonapp.data.model.Athlete
import com.biathlonapp.data.model.RaceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesRepository(private val context: Context) {

    private val db = FavoriteDatabase.getInstance(context)
    private val favoriteAthleteDao = db.favoriteAthleteDao()  // Переименовал
    private val favoriteResultDao = db.favoriteResultDao()    // Переименовал

    // ===== МЕТОДЫ ДЛЯ СПОРТСМЕНОВ =====

    // Получить всех избранных
    suspend fun getAllFavorites(): List<FavoriteAthlete> {
        return try {
            favoriteAthleteDao.getAllFavorites()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Получить избранных как Flow (для LiveData)
    fun getAllFavoritesFlow(): Flow<List<FavoriteAthlete>> = flow {
        emit(favoriteAthleteDao.getAllFavorites())
    }

    // Добавить в избранное
    suspend fun addToFavorites(athlete: Athlete): Boolean {
        return try {
            favoriteAthleteDao.insertFavorite(athlete.toFavoriteAthlete())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Добавить в избранное (из локальной модели)
    suspend fun addToFavorites(favoriteAthlete: FavoriteAthlete): Boolean {
        return try {
            favoriteAthleteDao.insertFavorite(favoriteAthlete)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Удалить из избранного
    suspend fun removeFromFavorites(athleteId: String): Boolean {
        return try {
            favoriteAthleteDao.deleteFavoriteById(athleteId)
            favoriteResultDao.deleteResultsForAthlete(athleteId)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Проверить, в избранном ли
    suspend fun isFavorite(athleteId: String): Boolean {
        return try {
            favoriteAthleteDao.isFavorite(athleteId)
        } catch (e: Exception) {
            false
        }
    }

    // Обновить данные спортсмена в избранном
    suspend fun updateFavoriteAthlete(athlete: Athlete): Boolean {
        return try {
            val existing = favoriteAthleteDao.getFavoriteById(athlete.athleteId ?: return false)
            if (existing != null) {
                val updated = existing.copy(
                    name = athlete.firstName ?: existing.name,
                    surname = athlete.lastName ?: existing.surname,
                    gender = athlete.gender ?: existing.gender,
                    sportsRank = athlete.sportsRank ?: existing.sportsRank,
                    birthDate = athlete.birthDate ?: existing.birthDate,
                    region = athlete.region ?: existing.region,
                    lastName = athlete.lastName ?: existing.lastName,
                    firstName = athlete.firstName ?: existing.firstName,
                    regionCode = athlete.regionCode ?: existing.regionCode,
                    lastUpdated = System.currentTimeMillis()
                )
                favoriteAthleteDao.insertFavorite(updated)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Получить одного избранного
    suspend fun getFavoriteAthleteById(athleteId: String): FavoriteAthlete? {
        return try {
            favoriteAthleteDao.getFavoriteById(athleteId)
        } catch (e: Exception) {
            null
        }
    }

    // ===== МЕТОДЫ ДЛЯ РЕЗУЛЬТАТОВ =====

    // Сохранить все результаты спортсмена
    suspend fun saveAthleteResults(athleteId: String, results: List<RaceResult>) {
        try {
            // Сначала удаляем старые результаты
            favoriteResultDao.deleteResultsForAthlete(athleteId)

            // Сохраняем новые
            val favoriteResults = results.mapNotNull { race ->
                race.athletePerformance?.let { performance ->
                    FavoriteResult(
                        athleteId = athleteId,
                        discipline = race.raceInfo?.discipline,
                        date = race.raceInfo?.date,
                        nameRace = race.raceInfo?.nameRace,
                        placeRace = race.raceInfo?.placeRace,
                        startNumber = performance.startNumber,
                        finishPlace = performance.finishPlace,
                        missCount = performance.missCount
                    )
                }
            }
            if (favoriteResults.isNotEmpty()) {
                favoriteResultDao.insertAllResults(favoriteResults)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Получить результаты из кэша
    suspend fun getCachedResults(athleteId: String): List<FavoriteResult> {
        return try {
            favoriteResultDao.getResultsForAthlete(athleteId)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Проверить, есть ли кэшированные результаты
    suspend fun hasCachedResults(athleteId: String): Boolean {
        return try {
            favoriteResultDao.getResultsForAthlete(athleteId).isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    // Удалить результаты при удалении спортсмена из избранного
    suspend fun deleteResultsForAthlete(athleteId: String) {
        try {
            favoriteResultDao.deleteResultsForAthlete(athleteId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
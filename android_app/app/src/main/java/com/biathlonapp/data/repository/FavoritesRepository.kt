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
    private val favoriteAthleteDao = db.favoriteAthleteDao()
    private val favoriteResultDao = db.favoriteResultDao()

    // ===== МЕТОДЫ ДЛЯ СПОРТСМЕНОВ =====

    suspend fun getAllFavorites(): List<FavoriteAthlete> {
        return try {
            favoriteAthleteDao.getAllFavorites()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getAllFavoritesFlow(): Flow<List<FavoriteAthlete>> = flow {
        emit(favoriteAthleteDao.getAllFavorites())
    }

    suspend fun addToFavorites(athlete: Athlete): Boolean {
        return try {
            favoriteAthleteDao.insertFavorite(athlete.toFavoriteAthlete())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun addToFavorites(favoriteAthlete: FavoriteAthlete): Boolean {
        return try {
            favoriteAthleteDao.insertFavorite(favoriteAthlete)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun removeFromFavorites(athleteId: String): Boolean {
        return try {
            favoriteAthleteDao.deleteFavoriteById(athleteId)
            deleteResultsForAthlete(athleteId) // Автоматически удаляем результаты
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun isFavorite(athleteId: String): Boolean {
        return try {
            favoriteAthleteDao.isFavorite(athleteId)
        } catch (e: Exception) {
            false
        }
    }

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

    suspend fun getFavoriteAthleteById(athleteId: String): FavoriteAthlete? {
        return try {
            favoriteAthleteDao.getFavoriteById(athleteId)
        } catch (e: Exception) {
            null
        }
    }

    // ===== МЕТОДЫ ДЛЯ РЕЗУЛЬТАТОВ =====

    suspend fun saveAthleteResults(athleteId: String, races: List<RaceResult>) {
        try {
            android.util.Log.d("CacheDebug", "Saving ${races.size} results for $athleteId")

            // Проверяем, существует ли спортсмен в избранном
            val athleteExists = favoriteAthleteDao.isFavorite(athleteId)

            if (!athleteExists) {
                android.util.Log.d("CacheDebug", "⚠️ Athlete $athleteId not in favorites, skipping save")
                return
            }

            // Удаляем старые результаты
            favoriteResultDao.deleteResultsForAthlete(athleteId)

            // Конвертируем новые результаты
            val favoriteResults = races.mapNotNull { race ->
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
                android.util.Log.d("CacheDebug", "✅ Saved ${favoriteResults.size} results")
            } else {
                android.util.Log.d("CacheDebug", "⚠️ No results to save")
            }
        } catch (e: Exception) {
            android.util.Log.e("CacheDebug", "❌ Error saving results: ${e.message}", e)
            e.printStackTrace()
        }
    }

    suspend fun getCachedResults(athleteId: String): List<FavoriteResult> {
        return try {
            val athleteExists = favoriteAthleteDao.isFavorite(athleteId)
            if (!athleteExists) {
                android.util.Log.d("CacheDebug", "⚠️ Athlete $athleteId not in favorites, returning empty results")
                return emptyList()
            }
            favoriteResultDao.getResultsForAthlete(athleteId)
        } catch (e: Exception) {
            android.util.Log.e("CacheDebug", "Error getting cached results: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun hasCachedResults(athleteId: String): Boolean {
        return try {
            val athleteExists = favoriteAthleteDao.isFavorite(athleteId)
            athleteExists && favoriteResultDao.getResultsForAthlete(athleteId).isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    // ✅ ДОБАВЛЯЕМ ЭТОТ МЕТОД
    suspend fun deleteResultsForAthlete(athleteId: String) {
        try {
            favoriteResultDao.deleteResultsForAthlete(athleteId)
            android.util.Log.d("CacheDebug", "🗑️ Deleted results for athlete $athleteId")
        } catch (e: Exception) {
            android.util.Log.e("CacheDebug", "Error deleting results: ${e.message}")
            e.printStackTrace()
        }
    }
}
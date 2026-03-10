package com.biathlonapp.data.local

import androidx.room.*

@Dao
interface FavoriteAthleteDao {

    @Query("SELECT * FROM favorite_athletes ORDER BY dateAdded DESC")
    suspend fun getAllFavorites(): List<FavoriteAthlete>

    @Query("SELECT * FROM favorite_athletes WHERE athleteId = :athleteId")
    suspend fun getFavoriteById(athleteId: String): FavoriteAthlete?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(athlete: FavoriteAthlete)

    @Delete
    suspend fun deleteFavorite(athlete: FavoriteAthlete)

    @Query("DELETE FROM favorite_athletes WHERE athleteId = :athleteId")
    suspend fun deleteFavoriteById(athleteId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_athletes WHERE athleteId = :athleteId)")
    suspend fun isFavorite(athleteId: String): Boolean

    @Query("UPDATE favorite_athletes SET lastUpdated = :timestamp WHERE athleteId = :athleteId")
    suspend fun updateLastUpdated(athleteId: String, timestamp: Long)
}
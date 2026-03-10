package com.biathlonapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: FavoriteResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllResults(results: List<FavoriteResult>)

    @Query("SELECT * FROM favorite_results WHERE athleteId = :athleteId ORDER BY date DESC")
    suspend fun getResultsForAthlete(athleteId: String): List<FavoriteResult>

    @Query("DELETE FROM favorite_results WHERE athleteId = :athleteId")
    suspend fun deleteResultsForAthlete(athleteId: String)

    @Query("SELECT COUNT(*) FROM favorite_results WHERE athleteId = :athleteId")
    suspend fun hasResults(athleteId: String): Int
}
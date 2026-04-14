package com.biathlonapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<CachedNews>)

    @Query("SELECT * FROM cached_news ORDER BY date DESC")
    suspend fun getAllNews(): List<CachedNews>

    @Query("DELETE FROM cached_news")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cached_news")
    suspend fun getCount(): Int
}
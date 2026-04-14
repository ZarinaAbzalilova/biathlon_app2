package com.biathlonapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteAthlete::class, FavoriteResult::class, CachedNews::class],  // Добавили FavoriteResult
    version = 3,  // Увеличили версию БД
    exportSchema = false
)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteAthleteDao(): FavoriteAthleteDao
    abstract fun favoriteResultDao(): FavoriteResultDao
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_athletes_db"
                ).fallbackToDestructiveMigration()  // Важно! Добавляем для обновления версии
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
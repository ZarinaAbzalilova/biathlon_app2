package com.biathlonapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteAthlete::class, FavoriteResult::class],  // Добавили FavoriteResult
    version = 2,  // Увеличили версию БД
    exportSchema = false
)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteAthleteDao(): FavoriteAthleteDao
    abstract fun favoriteResultDao(): FavoriteResultDao  // Добавили Dao для результатов

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
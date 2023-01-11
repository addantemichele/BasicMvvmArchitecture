package com.example.basicmvvmarchitecture.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.basicmvvmarchitecture.model.AnimeModel

@Database(entities = [AnimeModel::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract val animeDao: AnimeDao

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null
        fun getInstance(context: Context): AnimeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnimeDatabase::class.java,
                        "anime_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}

package com.example.basicmvvmarchitecture.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.basicmvvmarchitecture.model.AnimeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert
    suspend fun insertAnime(animeModel: AnimeModel) : Long
    @Update
    suspend fun updateAnime(animeModel: AnimeModel) : Int
    @Delete
    suspend fun deleteAnine(animeModel: AnimeModel) : Int
    @Query("SELECT * FROM anime_data_table")
    fun getAllAnime(): Flow<List<AnimeModel>>
    @Query("DELETE FROM anime_data_table")
    suspend fun deleteAllAnime()
}
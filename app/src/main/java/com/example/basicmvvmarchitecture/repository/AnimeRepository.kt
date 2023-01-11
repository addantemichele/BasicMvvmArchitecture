package com.example.basicmvvmarchitecture.repository

import com.example.basicmvvmarchitecture.db.AnimeDao
import com.example.basicmvvmarchitecture.model.AnimeModel

class AnimeRepository(private val animeDao: AnimeDao) {

    val animeList = animeDao.getAllAnime()

    suspend fun insert(animeModel: AnimeModel) : Long {
        return  animeDao.insertAnime(animeModel)
    }

    suspend fun update(animeModel: AnimeModel) : Int {
        return animeDao.updateAnime(animeModel)
    }

    suspend fun delete(animeModel: AnimeModel){
        animeDao.deleteAllAnime()
    }

    suspend fun deleteAll(){
        animeDao.deleteAllAnime()
    }

}
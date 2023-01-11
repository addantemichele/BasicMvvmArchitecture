package com.example.basicmvvmarchitecture.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_data_table")
data class AnimeModel (

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title : String,
    var numberOfEpisodes : Int
)
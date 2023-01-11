package com.example.basicmvvmarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basicmvvmarchitecture.model.AnimeModel
import com.example.basicmvvmarchitecture.network.ApiClient
import com.example.basicmvvmarchitecture.network.ApiAnimeInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeLiveRepository(private val apiAnimeInterface: ApiAnimeInterface) {

    val animeList = getAllAnime()

    fun insertAnime(animeModel: AnimeModel, onResult : (AnimeModel?) -> Unit )  {

        apiAnimeInterface?.insertAnime(animeModel)?.enqueue( object :Callback<AnimeModel>{

            override fun onFailure(call: Call<AnimeModel>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(call: Call<AnimeModel>, response: Response<AnimeModel>) {
                if(response.code() == 200 && response.body() != null){
                   onResult(response.body())
                }
                else{
                    onResult(null)
                }
            }
        })
    }

    private fun getAllAnime(): LiveData<List<AnimeModel>> {
        val data = MutableLiveData<List<AnimeModel>>()

        apiAnimeInterface?.getAllAnime()?.enqueue(object : Callback<List<AnimeModel>> {
            override fun onFailure(call: Call<List<AnimeModel>>, t: Throwable) {
                data.value = emptyList()
            }
            override fun onResponse(
                call: Call<List<AnimeModel>>,
                response: Response<List<AnimeModel>>
            ) {

                val res = response.body()!!
                if (response.code() == 200 && res != null) {
                    data.value = res
                } else {
                    data.value = emptyList()
                }
            }
        })
        return data
    }


}
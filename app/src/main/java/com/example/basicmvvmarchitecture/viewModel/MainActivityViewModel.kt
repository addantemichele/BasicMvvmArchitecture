package com.example.basicmvvmarchitecture.viewModel

import androidx.lifecycle.*
import com.example.basicmvvmarchitecture.Event
import com.example.basicmvvmarchitecture.model.AnimeModel
import com.example.basicmvvmarchitecture.repository.AnimeLiveRepository
import com.example.basicmvvmarchitecture.repository.AnimeRepository
import kotlinx.coroutines.launch


class MainActivityViewModel(private val repository : AnimeLiveRepository) : ViewModel() {

    private var animeList = MutableLiveData<List<AnimeModel>>()
    private var newAnime = MutableLiveData<AnimeModel?>()

    var animeTitle = MutableLiveData<String>()
    val animeNumOfEpisodes = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val cleanButtonText = MutableLiveData<String>()

    private var statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        cleanButtonText.value = "Clean"
        animeList = repository.animeList as MutableLiveData<List<AnimeModel>>
    }

    fun saveOrUpdate(){
        if(validateInput()){
            val title =  animeTitle.value!!
            val numOfEpisodes = (animeNumOfEpisodes.value!!).toInt()
            val newAnime = AnimeModel(0,title,numOfEpisodes)
            insertAnime(newAnime)
            animeTitle.value = ""
            animeNumOfEpisodes.value = ""
        }
    }

    private fun validateInput() : Boolean {
        return if(animeTitle.value == null){
            statusMessage.value = Event("Insert Anime Title")
            false
        }
        else  if(animeNumOfEpisodes.value == null || animeNumOfEpisodes.value?.toInt()!! <= 0){
            statusMessage.value = Event("Insert a valid number of Episodes")
            false
        }
        else{
            true
        }
    }

    fun cleanAll(){
        animeTitle.value = ""
        animeNumOfEpisodes.value = ""
    }

    /*fun getSavedAnime() = liveData {
        repository.animeList.collect {
            emit(it)
        }
    }*/

    fun getSavedAnime() : MutableLiveData<List<AnimeModel>>{
        return animeList
    }

    private fun insertAnime(animeModel: AnimeModel) = viewModelScope.launch {
        repository.insertAnime(animeModel) {
            if (it != null) {
                updateListWhenInsertAnime(it)
                statusMessage.value = Event("New anime Inserted Successfully with ID ${it.id}")
            }
        }
    }

    private fun updateListWhenInsertAnime(animeModel: AnimeModel) {
        var list = animeList.value?.toMutableList()
        list?.add(animeModel)
        animeList.value =list?.toList()
    }
}

class MainActivityViewModelFactory( private val repository: AnimeLiveRepository ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
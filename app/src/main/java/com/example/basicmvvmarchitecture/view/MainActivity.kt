package com.example.basicmvvmarchitecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicmvvmarchitecture.R
import com.example.basicmvvmarchitecture.databinding.ActivityMainBinding
import com.example.basicmvvmarchitecture.model.AnimeModel
import com.example.basicmvvmarchitecture.network.ApiAnimeInterface
import com.example.basicmvvmarchitecture.network.ApiClient
import com.example.basicmvvmarchitecture.repository.AnimeLiveRepository
import com.example.basicmvvmarchitecture.viewModel.MainActivityViewModel
import com.example.basicmvvmarchitecture.viewModel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var viewModel : MainActivityViewModel
    private lateinit var adapter : AnimeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //val dao = AnimeDatabase.getInstance(application).animeDao
        //val repository = AnimeRepository(dao)

        val apiPostInterface = ApiClient.getApiClient().create(ApiAnimeInterface::class.java)
        val repository = AnimeLiveRepository(apiPostInterface)

        val factory = MainActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory)[MainActivityViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled().let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        initAnimeRecyclerView()
    }

    private fun initAnimeRecyclerView(){
        binding.animeRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnimeRecyclerViewAdapter({ selectedItem: AnimeModel ->listItemClicked(selectedItem)})
        binding.animeRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        viewModel.getSavedAnime().observe(this, Observer {
            if (it != null) {
                adapter.setList(it)
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(animeModel: AnimeModel){
        Toast.makeText(this,"selected name is ${animeModel.title}",Toast.LENGTH_LONG).show()
    }




}